/*
 * Copyright (C) 2014 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.b3p.kar.inform;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import nl.b3p.kar.Mailer;
import nl.b3p.kar.hibernate.InformMessage;
import nl.b3p.kar.hibernate.RoadsideEquipment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Meine Toonen
 */
public class CarrierInformer implements Job {

    private static final Log log = LogFactory.getLog(CarrierInformer.class);

    public void execute(JobExecutionContext jec) throws JobExecutionException {

        String url = jec.getJobDetail().getJobDataMap().getString(CarrierInformerListener.PARAM_INFORM_CARRIERS_APPLICATION_URL);
        String host = jec.getJobDetail().getJobDataMap().getString(CarrierInformerListener.PARAM_INFORM_CARRIERS_HOST);
        String fromAddress = jec.getJobDetail().getJobDataMap().getString(CarrierInformerListener.PARAM_INFORM_CARRIERS_FROMADDRESS);
        run(url,host, fromAddress);
    }

    public void run(String url, String host, String fromAddress) {
        try {
            Stripersist.requestInit();
            EntityManager em = Stripersist.getEntityManager();
            List<InformMessage> messages = em.createQuery("From InformMessage where mailSent = false", InformMessage.class).getResultList();
            for (InformMessage message : messages) {
                createMessage(message, url,host, fromAddress);
                em.persist(message);
            }
            em.getTransaction().commit();
        } catch(Exception ex){
            log.error("Cannot create messages: ",ex);
        } finally {
            Stripersist.requestComplete();
        }
    }

    private void createMessage(InformMessage inform, String appUrl, String host, String fromAddress) {

        if (inform.getVervoerder().getEmail() != null) {
                String to = inform.getVervoerder().getEmail();
                String subject = "[Kar Geo Tool] Nieuwe KAR-gegevens";
                String body = getBody(inform, appUrl);
                String informerMail = inform.getAfzender().getEmail() != null ? inform.getAfzender().getEmail() : "";
                try {
                    Mailer.sendMail("KAR Geo Tool", fromAddress, subject, body,informerMail,"", to,informerMail);
                    inform.setMailSent(true);
                } catch (Exception ex) {
                    log.error("Cannot send inform message:", ex);
                    inform.setMailSent(false);
                }

                inform.setSentAt(new Date());
            
        } else {
            log.error("Vervoerder heeft geen mailadres ingevuld. Bericht niet verzonden aan: " + inform.getVervoerder().getFullname() + " - " + inform.getVervoerder().getUsername());
        }
    }
    
    private String getBody(InformMessage inform, String appUrl) {
        RoadsideEquipment rseq = inform.getRseq();
        String name = inform.getVervoerder().getFullname() != null ? inform.getVervoerder().getFullname()  : "vervoeder";
        String body = "";
        body += "Beste " + name + ", <br/>";
        body += "<br/>";
        body += "De KAR gegevens van het kruispunt " + rseq.getDescription() + " | " + rseq.getCrossingCode() + " met KAR-adres " + rseq.getKarAddress() + ", van "+ rseq.getDataOwner().getOmschrijving() +" zijn gewijzigd.<br/>";
        
        if(inform.getVervoerder().getUsername().equalsIgnoreCase("INCAA Priodeck")){
            body += "Een INCAA export kunt u, als u bent ingelogd, downloaden via <a href=\"" + appUrl + "/action/export?exportPtx=true&rseq=" + rseq.getId() + "\">deze link.</a><br/>";
        }else{
            body += "Een KV9 export kunt u, als u bent ingelogd, downloaden via <a href=\"" + appUrl + "/action/export?exportXml=true&rseq=" + rseq.getId() + "\">deze link.</a><br/>";
        }
        
        body += "Een overzicht van de VRI op de kaart vindt u, als u bent ingelogd, <a href=\"" + appUrl + "/action/editor?view=true#&rseq=" + rseq.getId() + "&x="+rseq.getLocation().getX() +"&y="+rseq.getLocation().getY()+"&zoom=12\">hier</a>.<br/><br/>";
        body += "Wij verzoeken u nadat u de KAR-gegevens hebt verwerkt dit aan te geven op de overzichtspagina.  Deze kunt u bereiken via deze <a href=\"" + appUrl + "/action/overview?carrier=true\">link</a>.<br/><br/>";
        body += "Met vriendelijke groet, <br/>";
        body += inform.getAfzender().getFullname() != null ?inform.getAfzender().getFullname()  : inform.getAfzender().getUsername();
        
        return body;
    }
}
