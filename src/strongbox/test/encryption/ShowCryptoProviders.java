package strongbox.test.encryption;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

public class ShowCryptoProviders
{
    private static final String EOL = System.getProperty("line.separator");

    public static void main(final String[] args)
    {
        final Provider[] providers = Security.getProviders();
        final Boolean verbose = Arrays.asList(args).contains("-v");
        for (final Provider p : providers)
        {
            System.out.format("%s %s%s", p.getName(), p.getVersion(), EOL);
            for (final Object o : p.keySet())
            {
                if (verbose)
                {
                    System.out.format("\t%s : %s%s", o, p.getProperty((String)o), EOL);
                }
            }
        }
    }
}