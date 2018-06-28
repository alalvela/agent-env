package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class NodeManagerBean implements NodeManagerBeanLocal {

	@EJB
    private NodeRepositoryBeanLocal nodeRepository;

	
}
