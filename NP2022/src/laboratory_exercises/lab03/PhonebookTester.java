package laboratory_exercises.lab03;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidNameException extends InvalidFormatException {
    String name;

    public InvalidNameException(String message) {
        super(message);
        this.name = message;
    }
}

class InvalidNumberException extends InvalidFormatException {
    public InvalidNumberException(String message) {
        super(message);
    }
}

class MaximumSizeExceddedException extends InvalidFormatException {
    public MaximumSizeExceddedException(String message) {
        super(message);
    }
}

class InvalidFormatException extends Exception {
    public InvalidFormatException(String message) {
        super(message);
    }
}

class Contact {
    private String name;
    private String[] phoneNumber;
    public final static int MAX_NUMBERS = 5;
    private int numberOfContacts;

    public Contact(String name, String... phonenumber) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {
        if (!validName(name))
            throw new InvalidNameException("");
        if (!validNumbers(phonenumber))
            throw new InvalidNumberException("invalid number");
        if (phonenumber.length > MAX_NUMBERS)
            throw new MaximumSizeExceddedException("");
        phoneNumber = Arrays.copyOf(phonenumber, MAX_NUMBERS);
        this.name = name;
        numberOfContacts = phonenumber.length;
    }

    private boolean validName(String name) {
        if (name.length() <= 4 || name.length() > 10)
            return false;
        for (char c : name.toCharArray())
            if (!Character.isLetterOrDigit(c))
                return false;
        return true;
    }

    private boolean validNumbers(String... numbers) {
        for (String number : numbers) {
            if (number.length() != 9)
                return false;
            for (char digit : number.toCharArray())
                if (!Character.isDigit(digit))
                    return false;
            if (!validDigit(number.charAt(2)))
                return false;
        }
        return true;
    }

    private boolean validDigit(char c) {
        return c == '0' || c == '1' || c == '2' || c == '5' || c == '6' || c == '7' || c == '8';
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        String[] copy = Arrays.copyOf(phoneNumber, numberOfContacts);
        return Arrays.stream(copy).sorted().toArray(String[]::new);
    }

    public void addNumber(String phonenumber) throws InvalidNameException, MaximumSizeExceddedException {
        if (!validNumbers(phonenumber))
            throw new InvalidNameException("");
        if (numberOfContacts == MAX_NUMBERS)
            throw new MaximumSizeExceddedException("");
        phoneNumber[numberOfContacts++] = phonenumber;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName()).append("\n");
        stringBuilder.append(numberOfContacts).append("\n");
        for (String number : getNumbers())
            if (number != null)
                stringBuilder.append(number).append("\n");
        return stringBuilder.toString();
    }

    public static Contact valueOf(String s) {
        String[] parts = s.split("\n");
        String[] nums = parts[1].substring(1, parts[1].length() - 1).split(", ");
        try {
            return new Contact(parts[0], nums);
        } catch (InvalidFormatException e) {
            return null;
        }
    }

}

class PhoneBook {
    public static final int MAX_CONTACTS = 250;
    private Contact[] contacts;
    private int numberOfContacts;
    private final Comparator<Contact> nameComaprator = Comparator.comparing(Contact::getName);

    public PhoneBook() {
        contacts = new Contact[MAX_CONTACTS];
        numberOfContacts = 0;
    }

    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
        if (numberOfContacts == MAX_CONTACTS)
            throw new MaximumSizeExceddedException("");
        for (Contact contact1 : contacts) {
            if (contact1 != null)
                if (contact1.getName().equals(contact.getName()))
                    throw new InvalidNameException(contact.getName());
        }

        contacts[numberOfContacts++] = contact;
    }

    public Contact getContactForName(String name) {
        for (Contact contact : contacts)
            if (contact != null)
                if (contact.getName().equals(name))
                    return contact;
        return null;
    }

    public int numberOfContacts() {
        return numberOfContacts;
    }

    public Contact[] getContacts() {
        Contact[] copy = Arrays.copyOf(contacts, numberOfContacts);
        return Arrays.stream(copy).filter(Objects::nonNull).sorted(nameComaprator).toArray(Contact[]::new);
    }

    public boolean removeContact(String name) {
        int index = -1;
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i] != null) {
                if (contacts[i].getName().equals(name)) {
                    index = i;
                    break;
                }
            }

        }
        if (index == -1)
            return false;
        //shift elements
        for (int i = index; i < numberOfContacts; i++)
            contacts[i] = contacts[i + 1];
        contacts[numberOfContacts--] = null;
        return true;
    }

    public static boolean saveAsTextFile(PhoneBook phonebook, String path) {
        File file = new File(path);
        PrintWriter printWriter;
        try {
            if (!file.exists())
                file.createNewFile();
            printWriter = new PrintWriter(new FileOutputStream(path));
            printWriter.print(phonebook.toString());
        } catch (IOException e) {
            return false;
        }
        printWriter.close();
        return true;
    }

    public static PhoneBook loadFromTextFile(String path) {
        File file = new File(path);
        PhoneBook result = new PhoneBook();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                line += "\n";
                line += br.readLine();
                Contact c = Contact.valueOf(line);
                result.addContact(c);
            }
        } catch (InvalidFormatException e) {

        } catch (IOException e) {

        }
        return result;
    }

    private boolean containsContact(Contact contact, Contact[] result) {
        return Arrays.stream(result).filter(Objects::nonNull).anyMatch(i -> i.getName().equals(contact.getName()));
        /*for (Contact c : result)
            if (c != null)
                if (contact.getName().equals(c.getName()))
                    return true;
        return false;*/
    }

    public Contact[] getContactsForNumber(String number_prefix) {
        Contact[] result = new Contact[numberOfContacts];
        int resultNumberOfContacts = 0;

        for (Contact contact : contacts) {
            if (contact != null) {
                for (String number : contact.getNumbers()) {
                    if (number.contains(number_prefix)) {
                        if (!containsContact(contact, result))
                            result[resultNumberOfContacts++] = contact;
                    }
                }
            }
        }

        //result = Arrays.copyOf(result, resultNumberOfContacts);
        return Arrays.stream(result).filter(Objects::nonNull).sorted(nameComaprator).toArray(Contact[]::new);
    }

    @Override
    public String toString() {
        return Arrays.stream(getContacts()).map(s -> String.format("%s\n", s)).collect(Collectors.joining(""));
/*        String s = "";
        for (Contact contact : getContacts()) {
            s += contact + "\n";
        }
        return s;*/
    }
}

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine())
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");

		/*exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
*/
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}
