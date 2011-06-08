package de.lbb.mentoring.example.jndi;

import de.lbb.mentoring.example.model.Employee;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class JNDITest
{
    private static final Logger logger = LoggerFactory.getLogger(JNDITest.class);

    /**
     * - Reading the JNDI context from a running jboss instance
     * - the url and port where to find the jndi context is set in src/main/resources/jndi.properties
     *
     * Beware: only "public" jndi entries are listed
     *
     * @throws NamingException .
     */
    //@Test
    public void testJNDI() throws NamingException {

        Context ctx = new InitialContext();
        logger.info("JNDI-Context-Listing:");
        showJndiContext(ctx, "", "");
        ctx.close();
    }

    /**
     * Setting and reading some object from jndi context
     * @throws NamingException .
     */
    //@Test
    public void testJNDI2() throws NamingException {

        Context ctx = new InitialContext();
        logger.info("JNDI-Context-Listing:");


        ctx.rebind("DanielsSecret", "pssst");

        Object lookup = ctx.lookup("DanielsSecret");
        logger.info("lookup: {}", lookup);

        ctx.rebind("Employee", new Employee());
        lookup = ctx.lookup("Employee");
        logger.info("lookup: {}", lookup);

        ctx.close();
    }

    public static void showJndiContext(Context ctx, String name, String space) {
        if (null == name)
            name = "";
        if (null == space)
            space = "";

        try {
            NamingEnumeration<NameClassPair> en = ctx.list(name);
            while (en != null && en.hasMoreElements()) {
                String delim = (name.length() > 0) ? "/" : "";
                NameClassPair ncp = en.next();
                logger.info(space + name + delim + ncp);
                if (space.length() < 40)
                    showJndiContext(ctx, ncp.getName(), "    " + space);
            }
        } catch (javax.naming.NamingException ex) {
//			logger.error("", ex);
        }
    }

}
