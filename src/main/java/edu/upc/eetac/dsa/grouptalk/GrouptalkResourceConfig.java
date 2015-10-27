package edu.upc.eetac.dsa.grouptalk;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.Set;

/**
 * Created by ruben on 24/10/15.
 */

public class GrouptalkResourceConfig extends ResourceConfig {



    public GrouptalkResourceConfig() {

        register(RolesAllowedDynamicFeature.class);

        packages("edu.upc.eetac.dsa.grouptalk");
        packages("edu.upc.eetac.dsa.grouptalk.auth");

    }

}
