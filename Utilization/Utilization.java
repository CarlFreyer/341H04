import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class Utilization{
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        Connection connected = null;
        boolean logedIn = false;
        while(!logedIn){
            String username;
            String pass;
            System.out.println("\n\nUsername:");
            username = in.nextLine();
            System.out.println("\n\nPassword:");
            pass = in.nextLine();
            System.out.println("\n\nUsername: " + username + "\nPassword: " + pass);
            try{
                System.out.println("\n\nAttempting connection to database...");
                connected = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", username, pass);
                logedIn = true;
                System.out.println("Attempt succesful!");
                boolean roomFound = false;
                String building = null;
                String roomNum = null;
                while(!roomFound){
                    try(Statement s = connected.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                        System.out.println("\n\nBuilding:");
                        String buildingIn = in.nextLine();
                        if(buildingIn.indexOf(39) != -1){
                            System.out.println("\n\nPlease do not use ' in your input.");
                            continue;
                        }
                        String q = "select distinct building from classroom where building = " + "'" + buildingIn + "'";
                        try(ResultSet result = s.executeQuery(q)){
                            if(!result.next()){
                                System.out.println("\n\nThat building does not exist.\nTry again");
                                continue;
                            }else {
                                building = result.getString("building");
                                if(!result.next()){
                                    System.out.println("\n\nThe building " + building + " was found!");
                                }else{
                                    building = null;
                                    continue;
                                }
                            }
                        }
                        System.out.println("\n\nRoom Number:");
                        String roomNumIn = null;
                        if(in.hasNextInt()){
                            roomNumIn = in.nextLine();
                        }else{
                            in.nextLine();
                            System.out.println("\n\nPlease integer input only.");
                            continue;
                        }
                        q = "select room_number from classroom where building = " + "'" + building + "' and room_number=" + roomNumIn;
                        try(ResultSet result = s.executeQuery(q)){
                            if(!result.next()){
                                System.out.println("\n\nThat room number does not exist in " + building + ".\nTry again");
                                continue;
                            }else {
                                roomNum = result.getString("room_number");
                                if(!result.next()){
                                    System.out.println("\n\nThe room number " + roomNum + " was found!");
                                    roomFound = true;
                                    in.close();
                                }else{
                                    roomNum = null;
                                    continue;
                                }
                            }
                        }
                        q = "select year, semester, day, start_hr, start_min from (select year, semester, day, start_hr, start_min,  case     when semester = 'Fall' then 0     when semester = 'Spring' then 1     else null end as semester_number, case     when day = 'M' then 0     when day = 'T' then 1     when day = 'W' then 2     when day = 'R' then 3     when day = 'F' then 4     else null end as day_number from section join time_slot using(time_slot_id) where building = '" + building + "' and room_number = " + roomNum + " order by year, semester_number, day_number, start_hr, start_min)";
                        try(ResultSet result = s.executeQuery(q)){
                            if(!result.next()){
                                System.out.println("\n\n" + building + " " + roomNum + " has never been used.");
                            }else {
                                int firstYear = result.getInt("year");
                                result.last();
                                int lastYear = result.getInt("year");
                                System.out.println("\n\n" + building + " " + roomNum + " has been used between " + firstYear + " - " + lastYear + ".");
                                result.first();
                                System.out.printf("\n%4s\t%8s\t%3s\t%10s\t%12s\n", "Year", "Semester", "Day", "Start Hour", "Start Minute");
                                do{
                                    System.out.printf("%4s\t%8s\t%3s\t%10s\t%12s\n", result.getString("year"), result.getString("semester"), result.getString("day"), result.getString("start_hr"), result.getString("start_min"));
                                }while(result.next());
                            }
                        }
                    }catch(SQLException e){
                        System.out.println("\n\nAn error occured.\nTerminating Program");
                        return;
                    }
                }
            }catch (SQLException e){
                System.out.println("\n\nUsername or password is incorrect :(.\nPlease try again.");
            }
        }
        in.close();
    }
}