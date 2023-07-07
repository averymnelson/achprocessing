import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

class ACHSystem {
    ArrayList<Account> accounts;
    ArrayList<Account> checklist;
    ArrayList<String> list;
    ArrayList<String> checks;
    ArrayList<Account> found;
    LocalDate d;
    String dating;

    public ACHSystem() {
        accounts = new ArrayList<Account>();
        checklist = new ArrayList<Account>();
        list = new ArrayList<String>();
        checks = new ArrayList<String>();
        found = new ArrayList<Account>();
        d = LocalDate.now();
        dating = d.toString();
    }

    public void readFile() {
        // reading bank info
        try {
            BufferedReader in = new BufferedReader(new FileReader("banking.txt"));
            String readin;
            while ((readin = in.readLine()) != null)
                list.add(readin);

            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        // reading ach list to import
        try {
            BufferedReader in = new BufferedReader(new FileReader("importing.txt"));
            String readin;
            while ((readin = in.readLine()) != null)
                checks.add(readin);

            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        // entering to bank account list
        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i);
            String[] arrOfStr = str.split("\\t");
            Account temp = new Account();
            temp.setVendor(arrOfStr[0]);
            temp.setRT(arrOfStr[1]);
            temp.setAcctNum(arrOfStr[2]);
            temp.setType(arrOfStr[5]);
            accounts.add(temp);
        }
        // entering to arraylist for checks
        for (int i = 1; i < checks.size(); i++) {
            String endstr = "";
            String str = checks.get(i);
            try {
                String[] arrOfStr = str.split("\\t");
                for (int j = 0; j < arrOfStr.length; j++) {
                    arrOfStr[j] = arrOfStr[j].trim();
                }
                String[] arr = arrOfStr[arrOfStr.length - 1].split(",");
                if (arrOfStr[2] != "") {
                    String[] removed = arrOfStr[2].split("\"");
                    if (removed[0] == "") {
                        endstr = removed[1];
                    } else {
                        endstr = removed[0];
                    }
                } else {
                    System.out.println("error found with parsing, text Avery");
                }
                Account temps = new Account();
                temps.setVendor(endstr);
                temps.setCheck(arrOfStr[1]);
                String temp1 = "";
                for (int j = 0; j < arr.length; j++) {
                    temp1 += arr[j];
                }
                temp1 = temp1.substring(1);
                temps.setAmount(temp1);
                checklist.add(temps);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // combining lists
    public void matching() {
        for (int i = 0; i < accounts.size(); i++) {
            for (int j = 0; j < checklist.size(); j++) {
                String checksvend = checklist.get(j).getVendor().trim();
                String acctvend = accounts.get(i).getVendor().trim();
                if (checksvend.equals(acctvend)) {
                    Account temp = checklist.get(j);
                    temp.setAcctNum(accounts.get(i).getAcctNum());
                    temp.setRT(accounts.get(i).getRT());
                    temp.setType(accounts.get(i).getType());
                    if (temp.getAcctNum() != "" && temp.getRT() != "" && temp.getType() != "" && temp.getVendor() != ""
                            && temp.getAmount() != "" && temp.getCheck() != "") {
                        found.add(temp);
                    }
                }
            }
        }
        for (int k = 0; k < found.size(); k++) {
            Account temp = found.get(k);
            checklist.remove(temp);
        }
    }

    // writing complete ach file
    public void write() {
        try {
            File file = new File("export" + dating + ".txt");
            System.setProperty("export" + dating + ".txt", "UTF-8");
            FileWriter fw = new FileWriter(file);
            fw.write("Name\tID\tAccount Number\tAccount Type\tR&T Number\tAmount" + "\n");
            for (int i = 0; i < found.size(); i++) {
                fw.write(found.get(i).printing());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    // printing error file
    public void printerr() {
        try {
            File file = new File("errors" + dating + ".txt");
            System.setProperty("errors" + dating + ".txt", "UTF-8");
            FileWriter fw = new FileWriter(file);
            fw.write("Consumer Name ID  Amount" + "\n");
            for (int i = 0; i < checklist.size(); i++) {
                fw.write(checklist.get(i).printing());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public static void main(String[] args) throws java.io.IOException {
        ACHSystem sys = new ACHSystem();
        Scanner sc = new Scanner(System.in);
        System.out.println("Running.");
        sys.readFile();
        sys.matching();
        sys.write();
        sys.printerr();
        sc.close();
    }
}