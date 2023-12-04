import components.map.Map;
import components.map.Map1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jeevan Nadella
 *
 */
public final class EmailAccount1 implements EmailAccount {

    String firstName;
    String lastName;
    String address;
    static Map<String, Integer> map = new Map1L<>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;

        if (map.hasKey(lastName.toLowerCase())) {
            Integer n = map.value(lastName.toLowerCase());
            n = n + 1;
            this.address = lastName.toLowerCase() + "." + n + "@osu.edu";
            map.replaceValue(lastName.toLowerCase(), n);
        } else {
            map.add(lastName.toLowerCase(), 1);
            this.address = lastName.toLowerCase() + ".1@osu.edu";
        }
    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        String fullName = this.firstName + " " + this.lastName;
        return fullName;

    }

    @Override
    public String emailAddress() {

        return this.address;

    }

    @Override
    public String toString() {

        String str = "Name: " + this.firstName + " " + this.lastName
                + ", Email: " + this.address;
        return str;

    }

}