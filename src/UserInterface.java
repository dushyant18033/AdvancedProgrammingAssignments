import java.util.*;

public class UserInterface
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
        Iterator it;
        student st;
        int n=s.nextInt();
        int m=0;
        int unplacedCtr=n;

        ArrayList<student> Students=new ArrayList<student>();
        ArrayList<company> Companies=new ArrayList<company>();
        HashMap<String,company> companyMap=new HashMap<String,company>();
        HashMap<Integer,student> studentMap=new HashMap<Integer,student>();

        for(int i=0; i<n; i++)
        {
            float cgpa=s.nextFloat();
            String branch=s.next();
            student s1=new student( cgpa, branch,i+1 );//cgpa,branch,rollno.
            Students.add(s1);
            studentMap.put(i+1,s1);
        }
        System.out.println("---- students registered ----");

        while(true)
        {
            int qtype=s.nextInt();

            switch (qtype)
            {
                case 1: //add company
                    String name=s.nextLine();
                    name=s.next();

                    System.out.print("Number of Eligible Courses = ");
                    int c=s.nextInt();
                    HashSet<String> eligibleBranches=new HashSet<String>();
                    for(int i=0; i<c; i++)
                        eligibleBranches.add(s.next());

                    System.out.print("Number of required students = ");
                    int st_req=s.nextInt();

                    company comp=new company(name,eligibleBranches,st_req);
                    Companies.add(comp);
                    companyMap.put(name,comp);

                    m++;
                    System.out.println(comp);

                    System.out.println("Enter scores for technical round.");
                    comp.findEligibleStudents(Students);
                    ArrayList<student> appliedStuds=comp.getApplied();

                    it=appliedStuds.iterator();
                    HashMap<student,Integer> scores=new HashMap<>();
                    while(it.hasNext())
                    {
                        st=(student) it.next();
                        System.out.print("Enter score for Roll no. "+st.getRollno()+": ");
                        int sc=s.nextInt();
                        scores.put(st,sc);
                        st.getAppliedIn().add(comp);
                        //System.out.println(st);
                        //System.out.println(scores.get(st));
                    }
                    comp.setTechScores(scores);

                    break;

                case 2: //Remove students who are placed
                    it=Students.iterator();
                    System.out.println("Accounts removed for");
                    while(it.hasNext())
                    {
                        st= (student) it.next();
                        if(st.isPlaced())
                        {
                            System.out.println(st.getRollno());
                            studentMap.remove(st.getRollno());
                            it.remove();
                        }
                    }
                    break;

                case 3: //Remove companies whose application is closed
                    it=Companies.iterator();
                    System.out.println("Accounts removed for");
                    while(it.hasNext())
                    {
                        company com= (company) it.next();
                        if(!com.isAppOpen())
                        {
                            System.out.println(com.getName());
                            companyMap.remove(com.getName());
                            it.remove();
                        }
                    }
                    break;

                case 4: //Number of unplaced students;
                    /*int ctr=0;
                    it=Students.iterator();
                    while(it.hasNext())
                    {
                        st=(student)it.next();
                        if(!st.isPlaced())
                            ctr++;
                    }*/
                    System.out.println(unplacedCtr+" students left.");
                    break;

                case 5: //Companies with application open
                    it=Companies.iterator();
                    while(it.hasNext())
                    {
                        company com= (company) it.next();
                        if(com.isAppOpen())
                            System.out.println(com.getName());
                    }
                    break;

                case 6: //selection
                    String comName=s.next();
                    if(!companyMap.containsKey(comName))
                    {
                        System.out.println("No company with the given name has an account.");
                        break;
                    }
                    company com=companyMap.get(comName);
                    ArrayList<student> selectedStuds=com.selectStudents();

                    unplacedCtr-=selectedStuds.size();  ///Maintain count of unplaced students.

                    System.out.println("Roll No Of Selected Students.");
                    it=selectedStuds.iterator();
                    while(it.hasNext())
                    {
                        System.out.println(((student)it.next()).getRollno());
                    }
                    break;

                case 7: //Details of company
                    String nm=s.next();
                    if(companyMap.containsKey(nm))
                        System.out.println((company)companyMap.get(nm));
                    else
                        System.out.println("No company with the given name has an account.");
                    break;

                case 8: //Details of student
                    int rn=s.nextInt();
                    if(studentMap.containsKey(rn))
                        System.out.println(studentMap.get(rn));
                    else
                        System.out.println("No student with the given roll number has an account.");
                    break;

                case 9: //Name of company and score obtained in techRound by the student
                    int roll=s.nextInt();
                    if(!studentMap.containsKey(roll))
                    {
                        System.out.println("No student with the given roll no has an account.");
                        break;
                    }
                    st=studentMap.get(roll);
                    HashSet appliedIn=st.getAppliedIn();
                    it=Companies.iterator();
                    while(it.hasNext())
                    {
                        comp=(company) it.next();
                        if(appliedIn.contains(comp))
                            System.out.println(comp.getName()+" "+comp.getTechScores().get(st));
                    }
                    break;
                /*case 10:    //For debugging purpose, Students that currently have active account.
                    it=Students.iterator();
                    while(it.hasNext())
                    {
                        student st1=(student)it.next();
                        System.out.println(st1);
                    }
                    break;
                case 11:    //For debugging purpose, Companies that currently have active account.
                    it=Companies.iterator();
                    while(it.hasNext())
                    {
                        company cm=(company) it.next();
                        System.out.println(cm);
                    }
                    break;*/
                case 10:    //Exit the program
                    System.out.print("Are you sure you want to exit?(y/n): ");
                    String rep=s.next();
                    if(rep.equals("y") || rep.equals("Y"))
                        System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
            if(unplacedCtr<=0)
            {
                System.out.println("It seems like all reqistered students have been placed.");
                System.out.print("Would you like to exit?(y/n): ");
                String rep=s.next();
                if(rep.equals("y") || rep.equals("Y"))
                    System.exit(0);
            }
        }
    }
}
