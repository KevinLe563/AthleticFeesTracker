package athleticfeestracker;

import java.util.*;
import java.io.*;

class Fee { // creates classes

    public String name;
    public String cost;
    public String paid;

    Fee(String a, String b, String c) {
        this.name = a;
        this.cost = b;
        this.paid = c;
    }
}

class Athlete {

    public String id;
    public String fname;
    public String lname;
    public List<Fee> fees = new ArrayList<Fee>();

    Athlete(String a, String b, String c) {
        this.fname = a;
        this.lname = b;
        this.id = c;
    }
}

class Team {

    public String name;
    public List<Athlete> athletes = new ArrayList<Athlete>();

    Team(String a) {
        this.name = a;
    }
}

public class AthleticFeesTracker {

    static List<Team> listofteams = new ArrayList<Team>();
    /* initiated an arraylist with the class Team */
    static List<Athlete> listofathletes = new ArrayList<Athlete>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        File f = new File("Data.txt");
        List<String> lines = new ArrayList<String>();
        if (f.exists() && !f.isDirectory()) { // searches whether the file does not exist in the given directory
            BufferedReader br = new BufferedReader(new FileReader("Data.txt")); // turns file into buffered reader
            String line;
            while ((line = br.readLine()) != null) { // reads the line of the file
                lines.add(line); //adds the string to the array lines
            }
            processinput(lines); //calls method
            getinput();
            appendtofile();
        } else {
            File parentDir = new File("Atheletics");
            parentDir.mkdir(); //makes it a directory
            String hash = "Data.txt";
            File file = new File(hash); //creates new file
            file.createNewFile();
            getinput(); //calls method
            appendtofile();
        }
    }

    private static void processinput(List<String> input) { //new method would the following parameter
        for (String line : input) { //loops through arraylist
            String lineidentifier = line.substring(0, line.indexOf(":")); //makes a string equal to the beginning of the line to the first :
            switch (lineidentifier) { // switch based on what string it is
                case "Team":
                    String randomteam = line.substring(line.indexOf(":") + 2); // makes everything after that colon a string
                    listofteams.add(new Team(randomteam)); //makes a new team based on the string after the colon
                    break;
                case "Player":
                    String randomplayer = line.substring(line.indexOf(":") + 2); // makes everything after the colon and a space a string
                    String[] playerstuff = randomplayer.split(" "); //splits the string of unputs into 3 separate strings between every space
                    Athlete randomathlete = new Athlete(playerstuff[0], playerstuff[1], playerstuff[2]); //makes the new object (athlete)
                    listofathletes.add(randomathlete);
                    listofteams.get(listofteams.size() - 1).athletes.add(randomathlete); //adds the player to the arraylist team
                    break;
                case "Fee":
                    String randomfee = line.substring(line.indexOf(":") + 2);
                    String[] feestuff = randomfee.split(" ");
                    Fee randomfee2 = new Fee(feestuff[0], feestuff[1], feestuff[2]);
                    listofathletes.get(listofathletes.size() - 1).fees.add(randomfee2);
                    break;
            }
        }
    }

    private static void getinput() throws FileNotFoundException {
        int x = 0;
        while (x != 1) {
            for (Team team : listofteams) {
                System.out.println(team.name);
            }
            System.out.println("Type the team you want to edit or enter 1 to create a new team or enter 2 to remove a team and its respective players and fees or enter anything else to append and exit the program");
            String userinput = input.nextLine();
            if (userinput.equals("1")) {
                System.out.println("Enter the name of the new team (make sure the name is only 1 word):");
                String newteamname = input.nextLine(); // takes in user input for the name of the team
                listofteams.add(new Team(newteamname)); // adds the new team to the list of teams
            } else if (userinput.equals("2")) {
                System.out.println("Which team would you like to remove? Please type the name of the team. Note that names are case sensitive.");
                int g = 1;
                for (int d = 0; d < listofteams.size(); d++) {
                    System.out.println(g + ". " + listofteams.get(d).name);
                    g++;
                }
                int userinput2 = input.nextInt();
                input.nextLine();
                userinput2 = userinput2 - 1;
                listofteams.remove(userinput2);
            } else {
                boolean valid = false;
                int validindex = 0;
                for (Team team : listofteams) {
                    if (team.name.equals(userinput)) {
                        valid = true;
                        break;
                    }
                    validindex++;
                }
                if (valid) {   
                    teamEdit(validindex);// calls method with an int parameter
                } else {
                    return; // exits the method
                }
            }

        }
    }

    private static void teamEdit(int i) { // method to edit teams and gain userinput

        System.out.println("Choose one of the following (enter the index number):");
        System.out.println("1. Edit the name of the team");
        System.out.println("2. Edit a current player");
        System.out.println("3. Enter a new player");
        System.out.println("4. Remove a player");
        String userinput3 = input.nextLine(); //obtains user input
        if (userinput3.equals("1")) {
            System.out.println("What is the new name of the team?"); //renames the team
            String userinput4 = input.nextLine();
            listofteams.get(i).name = userinput4; //renames the name attribute of the team
        } else if (userinput3.equals("2")) {
            System.out.println("Please select the index number of the player:");
            int j = 1;
            for (int n = 0; n < listofteams.get(i).athletes.size(); n++) { // prints the list of players of the team selected
                System.out.println(j + ". " + listofteams.get(i).athletes.get(n).fname + " " + listofteams.get(i).athletes.get(n).lname + " " + listofteams.get(i).athletes.get(n).id);
                j++;
            }
            int chosenathlete = input.nextInt();
            chosenathlete = chosenathlete - 1; // -1 since array starts at 0
            System.out.println("Please choose the index number of one of the following:");
            System.out.println("1. Edit Attribute");
            System.out.println("2. Add Fees");
            System.out.println("3. Check off paid fees");
            System.out.println("4. Remove an existing fee");
            input.nextLine();
            String userinput5 = input.nextLine(); //Broken
            if (userinput5.equals("1")) {
                System.out.println("Which attribute needs to be changed?");
                System.out.println("1. First Name");
                System.out.println("2. Last Name");
                System.out.println("3. ID number");
                String userinput6 = input.nextLine();
                if (userinput6.equals("1")) {
                    System.out.println("Enter the athletes new first name:");
                    String newname = input.nextLine();
                    listofteams.get(i).athletes.get(chosenathlete).fname = newname; //resets the first name of the athlete
                } else if (userinput6.equals("2")) {
                    System.out.println("Enter the athletes new last name:");
                    String newname = input.nextLine();
                    listofteams.get(i).athletes.get(chosenathlete).lname = newname;
                } else if (userinput6.equals("3")) {
                    System.out.println("Enter the athletes new ID number:");
                    String newid = input.nextLine();
                    listofteams.get(i).athletes.get(chosenathlete).id = newid; // resets the id number of the athlete
                } else {
                    System.out.println("That is not the right input. The program will now exit");
                    System.exit(0);
                }
            } else if (userinput5.equals("2")) {
                System.out.println("What is the name of the fee?"); // obtains the attributes of the fee from the user
                String feename = input.nextLine();
                System.out.println("What is the cost of the fee?");
                String feecost = input.nextLine();
                System.out.println("Paid/Owing?");
                String feepaid = input.nextLine();
                listofteams.get(i).athletes.get(chosenathlete).fees.add(new Fee(feename, feecost, feepaid)); // adds a new fee to the player
            } else if (userinput5.equals("3")) {
                System.out.println("Please select the index number of one of the fees below:");
                System.out.println(listofteams.get(i).athletes.get(chosenathlete).fname + "'s fees");
                int indexcounter = 1;
                for (int h = 0; h < listofteams.get(i).athletes.get(chosenathlete).fees.size(); h++) {
                    System.out.println(indexcounter + ". " + listofteams.get(i).athletes.get(chosenathlete).fees.get(h).name); // prints out all the fees
                    indexcounter++;
                }
                int feeselection = input.nextInt();
                input.nextLine();
                feeselection = feeselection - 1;
                listofteams.get(i).athletes.get(chosenathlete).fees.get(feeselection).paid = "Paid"; // makes the attribute paid read "Paid"
            } else if (userinput5.equals("4")) {
                System.out.println("Please select the index number of one of the fees below:");
                System.out.println(listofteams.get(i).athletes.get(chosenathlete).fname + "'s fees");
                int indexcounter = 1;
                for (int h = 0; h < listofteams.get(i).athletes.get(chosenathlete).fees.size(); h++) {
                    System.out.println(indexcounter + ". " + listofteams.get(i).athletes.get(chosenathlete).fees.get(h).name); // prints out all the fees
                    indexcounter++;
                }
                int feeselection = input.nextInt();
                input.nextLine();
                feeselection = feeselection - 1;
                listofteams.get(i).athletes.get(chosenathlete).fees.remove(feeselection); //removes the fee
            }
        } else if (userinput3.equals("3")) {
            System.out.println("What is the first name of the athlete?"); // obtains attributes from the user for the new athlete
            String athletefname = input.nextLine();
            System.out.println("What is the last name of the athlete?");
            String athletelname = input.nextLine();
            System.out.println("What is the athlete's ID?");
            String id = input.nextLine();
            listofteams.get(i).athletes.add(new Athlete(athletefname, athletelname, id)); // adds the object to the arraylist of athletes attribute of the team
        } else if (userinput3.equals("4")) {
            int athletecounter = 1;
            System.out.println("Which player would you like to remove? Select the index number.");
            for (int u = 0; u < listofteams.get(i).athletes.size(); u++) {
                System.out.println(athletecounter + ". " + listofteams.get(i).athletes.get(u).fname + " " + listofteams.get(i).athletes.get(u).lname + " " + listofteams.get(i).athletes.get(u).id); // prints the player
                athletecounter++;
            }
            int counter = input.nextInt();
            input.nextLine();
            counter = counter - 1;
            listofteams.get(i).athletes.remove(counter); // removes the player
        } else {
            System.out.println("That's not the right input. The program will now exit.");
            System.exit(0);
        }
    }

    private static void appendtofile() throws FileNotFoundException { //method that appends to the txt document
        PrintWriter writer = new PrintWriter("Data.txt");
        for (Team team : listofteams) {
            writer.println("Team: " + team.name); // writes to the txt documnet
            for (Athlete athlete : team.athletes) {
                writer.println("Player: " + athlete.fname + " " + athlete.lname + " " + athlete.id);
                for (Fee fee : athlete.fees) {
                    writer.println("Fee: " + fee.name + " " + fee.cost + " " + fee.paid);
                }
            }
        }
        writer.close(); // closes the writer
    }
}
