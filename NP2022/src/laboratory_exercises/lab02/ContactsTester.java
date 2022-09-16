package laboratory_exercises.lab02;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

enum Operator {
    VIP,
    ONE,
    TMOBILE
}

abstract class Contact {
    private String date;

    public Contact(String date) {
        this.date = date;
    }

    public boolean isNewerThan(Contact c) {
        return c.date.compareTo(date) < 0;
    }

    public abstract String getType();

}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return "Email";
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", email);
    }
}

class PhoneContact extends Contact {
    private String phone;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        int c = Integer.parseInt(String.valueOf(phone.charAt(2)));
        if (c == 0 || c == 1 || c == 2)
            return Operator.TMOBILE;
        else if (c == 5 || c == 6)
            return Operator.ONE;
        else
            return Operator.VIP;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", phone);
    }
}

class Student {
    private Contact[] contacts;
    private int contactCapacity;
    private int numberOfContacts;

    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;

    public Student(String firstName, String lastName, String city, int age, long index) {
        contactCapacity = 10;
        contacts = new Contact[contactCapacity];
        numberOfContacts = 0;

        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
    }

    public void addEmailContact(String date, String email) {
        if (numberOfContacts >= contactCapacity) {
            contacts = Arrays.copyOf(contacts, contactCapacity * 2);
            contactCapacity *= 2;
        }
        contacts[numberOfContacts++] = new EmailContact(date, email);
    }

    public void addPhoneContact(String date, String phone) {
        if (numberOfContacts >= contactCapacity) {
            contacts = Arrays.copyOf(contacts, contactCapacity * 2);
            contactCapacity *= 2;
        }
        contacts[numberOfContacts++] = new PhoneContact(date, phone);
    }

    public Contact[] getEmailContacts() {
        return Arrays.stream(contacts)
                .filter(Objects::nonNull)
                .filter(contact -> contact.getType().equals("Email")).toArray(Contact[]::new);
    }

    public Contact[] getPhoneContacts() {
        return Arrays.stream(contacts)
                .filter(Objects::nonNull)
                .filter(contact -> contact.getType().equals("Phone")).toArray(Contact[]::new);
    }

    public int getNumberOfContacts() {
        return numberOfContacts;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {
        int index = 0;
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i] != null && contacts[i].isNewerThan(contacts[index]))
                index = i;
        }
        return contacts[index];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("{\"ime\":\"%s\", ", this.firstName));
        stringBuilder.append(String.format("\"prezime\":\"%s\", ", this.lastName));
        stringBuilder.append(String.format("\"vozrast\":%d, ", this.age));
        stringBuilder.append(String.format("\"grad\":\"%s\", ", this.city));
        stringBuilder.append(String.format("\"indeks\":%d, ", this.index));
        stringBuilder.append(String.format("\"telefonskiKontakti\":%s, ", Arrays.toString(getPhoneContacts())));
        stringBuilder.append(String.format("\"emailKontakti\":%s}", Arrays.toString(getEmailContacts())));
        return stringBuilder.toString();
    }
}

class Faculty {
    private String name;
    private Student[] students;
    private final Comparator<Student> contactAndIndexComaprator = Comparator
            .comparing(Student::getNumberOfContacts)
            .thenComparing(Student::getIndex).reversed();

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = Arrays.copyOf(students, students.length);
    }

    public int countStudentsFromCity(String cityName) {
        return (int) Arrays.stream(students).filter(i -> i.getCity().equals(cityName)).count();
    }

    public Student getStudent(long index) {
        return Arrays.stream(students)
                .filter(student -> student.getIndex() == index)
                .collect(Collectors.toList()).get(0);
    }

    public double getAverageNumberOfContacts() {
        double result = 0;
        for (Student s : students)
            result += s.getNumberOfContacts();
        return result / students.length;
    }

    public Student getStudentWithMostContacts() {
        return Arrays.stream(students).sorted(contactAndIndexComaprator).collect(Collectors.toList()).get(0);
    }

    @Override
    public String toString() {
        return "{\"fakultet\":\"" + name + "\"" +
                ", \"studenti\":" + Arrays.toString(students) +
                '}';
    }
}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
