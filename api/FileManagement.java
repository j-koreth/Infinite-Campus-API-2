import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileManagement {
    public static String getDistrictCode(){
        String code = null;
        Path file = FileSystems.getDefault().getPath(".code.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            code = reader.readLine();
            reader.close();
            input.close();
        }
        catch(IOException exception){
            System.err.print(exception);
        }
        return code;
    }
    public static String getPassword(){
        String password = null;
        Path file = FileSystems.getDefault().getPath(".password.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            password = reader.readLine();
            reader.close();
            input.close();
        }
        catch(IOException exception){
            System.err.print(exception);
        }
        return password;
    }
    public static String getUsername(){
        String username = null;
        Path file = FileSystems.getDefault().getPath(".username.txt");
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            username = reader.readLine();
            reader.close();
            input.close();
        }
        catch(IOException exception){
            System.err.print(exception);
        }
        return username;
    }
    public static void deleteGrades(){
        File grades = new File("grades.txt");
        grades.delete();
    }
    public static void deleteExisting(){
        File existsu = new File("./.username.txt");
        File existspw = new File("./.password.txt");
        File existc = new File("./.code.txt");
        existc.delete();
        existsu.delete();
        existspw.delete();
    }
    public static boolean getExisting(){
        File existsu = new File("./.username.txt");
        File existspw = new File("./.password.txt");
        File existc = new File("./.code.txt");
        return existc.exists() && existsu.exists() && existspw.exists();
    }
    public static void saveDistrictCode() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter the District Code : ");
        String code = scan.readLine();
        createDistrictCodeFile(code);
    }
    public static void saveUsername() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter your username : ");
        String username = scan.readLine();
        createUsernameFile(username);
    }
    public static void savePasswords() throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter your password : ");
        String password = scan.readLine();
        createPasswordFile(password);
    }
    public static void createDistrictCodeFile(String code){
        if(!System.getProperty("os.name").contains("windows")) {
            try {
                PrintWriter codeFile = new PrintWriter(".code.txt");
                codeFile.write(code);
                codeFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
        }
        else{
            try {
                PrintWriter codeFile = new PrintWriter(".code.txt");
                codeFile.write(code);
                codeFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
            try {
                Runtime.getRuntime().exec("attrib +H .code.java");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createUsernameFile(String username){
        if(!System.getProperty("os.name").contains("windows")) {
            try {
                PrintWriter usernameFile = new PrintWriter(".username.txt");
                usernameFile.write(username);
                usernameFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
        }
        else{
            try {
                PrintWriter usernameFile = new PrintWriter(".username.txt");
                usernameFile.write(username);
                usernameFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
            try {
                Runtime.getRuntime().exec("attrib +H .username.java");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createPasswordFile(String password){
        if(!System.getProperty("os.name").contains("windows")) {
            try {
                PrintWriter passwordFile = new PrintWriter(".password.txt");
                passwordFile.write(password);
                passwordFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
        }
        else{
            try {
                PrintWriter passwordFile = new PrintWriter(".password.txt");
                passwordFile.write(password);
                passwordFile.close();
            } catch (FileNotFoundException e1) {
                System.out.println("RIP");
            }
            try {
                Runtime.getRuntime().exec("attrib +H .password.java");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
