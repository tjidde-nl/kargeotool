package nl.b3p.kar.stripes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/recover")
@StrictBinding
public class RecoveryActionBean implements ActionBean{


    private static final Log LOG = LogFactory.getLog(RecoveryActionBean.class);
    private static final String RecoverPage = "./recover.jsp";

    @DefaultHandler
    public Resolution view() throws Exception {
    	ForwardResolution fr = new ForwardResolution(RecoverPage);
    	fr.addParameter("test", "Angela is gek!");      	
    	return fr;
    }
    
    private ActionBeanContext context;
	@Override
	public void setContext(ActionBeanContext context) {
		this.context = context;
		
	}

	@Override
	public ActionBeanContext getContext() {		
		return this.context;
	}

}
