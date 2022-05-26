import java.util.*;

public class company
{
    private final String name;
    private final HashSet<String> courses;
    private final int req_students;
    private boolean appOpen;
    private ArrayList<student> applied;
    private HashMap<student,Integer> techScores;


    public String getName() {
        return name;
    }

    public HashSet<String> getCourses() {
        return courses;
    }

    public int getReq_students() {
        return req_students;
    }

    public boolean isAppOpen() {
        return appOpen;
    }

    public void setAppOpen(boolean appOpen) {
        this.appOpen = appOpen;
    }

    public ArrayList<student> getApplied() {
        return applied;
    }

    public void setApplied(ArrayList<student> applied) {
        this.applied = applied;
    }

    public HashMap<student, Integer> getTechScores() {
        return techScores;
    }

    public void setTechScores(HashMap<student, Integer> techScores) {
        this.techScores = techScores;
    }

    company(String name, HashSet<String> courses, int req_students)
    {
        this.name=name;
        this.courses=courses;
        this.req_students=req_students;
        this.appOpen=true;
        this.applied=new ArrayList<student>();
    }

    public String toString()
    {
        String courseStr="";
        Iterator it=courses.iterator();
        while(it.hasNext())
        {
            String course= (String) it.next();
            courseStr+="\n"+course;
        }

        String toPrint=name+"\nCourse Criteria"+courseStr;

        if(appOpen)
            toPrint+="\nNumber Of Required Students = "+req_students+"\nApplication Status = "+"OPEN";
        else
            toPrint+="\nApplication Status = "+"CLOSED";

        return toPrint;
    }


    public void findEligibleStudents(ArrayList<student> Students)
    {
        applied=new ArrayList<student>();
        Iterator it=Students.iterator();

        while(it.hasNext())
        {
            student st=(student)it.next();
            if((!st.isPlaced()) && courses.contains(st.getBranch()))
            {
                applied.add(st);
                st.getAppliedIn().add(this);
            }
        }
    }

    public ArrayList<student> selectStudents()
    {

        Iterator it=applied.iterator();
        while(it.hasNext())
        {
            student st=(student)it.next();
            if(st.isPlaced())
                it.remove();
        }

        ArrayList<student> select=new ArrayList<student>();

        for(int i=0; i<req_students; i++)
        {
            it=applied.iterator();
            student maxScore=null;
            while(it.hasNext())
            {
                student st=(student)it.next();

                if(maxScore==null)
                {
                    maxScore=st;
                }
                else if((int)techScores.get(maxScore)==(int)techScores.get(st))
                {
                    if(maxScore.getCgpa()<st.getCgpa())
                        maxScore=st;
                }
                else if((int)techScores.get(maxScore)<(int)techScores.get(st))
                {
                    maxScore=st;
                }

            }
            maxScore.setPlaced(true);
            maxScore.setPlacedIn(this.name);
            select.add(maxScore);
            applied.remove(maxScore);
        }
        this.applied=select;
        this.appOpen=false;
        return applied;
    }
}