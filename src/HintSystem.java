public final class HintSystem
{
    static class edge
    {
        int src;
        int dest;
        int wt;

        edge(int s,int d,int w)
        {
            src=s;
            dest=d;
            wt=w;
        }
        void print()
        {
            System.out.println(src+" "+dest+" "+wt);
        }
    }

    private edge[] E;
    private int[] optimalPath;

    HintSystem(Monster[] layout)
    {
        E=new edge[21]; //Hardcoded edges
        E[0]=new edge(0,1,layout[1].getLevel());
        E[1]=new edge(0,4,layout[4].getLevel());
        E[2]=new edge(0,7,layout[7].getLevel());
        E[3]=new edge(3,1,layout[1].getLevel());
        E[4]=new edge(3,4,layout[4].getLevel());
        E[5]=new edge(3,7,layout[7].getLevel());
        E[6]=new edge(6,1,layout[1].getLevel());
        E[7]=new edge(6,4,layout[4].getLevel());
        E[8]=new edge(6,7,layout[7].getLevel());

        E[9]=new edge(1,2,layout[2].getLevel());
        E[10]=new edge(1,5,layout[5].getLevel());
        E[11]=new edge(1,8,layout[8].getLevel());
        E[12]=new edge(4,2,layout[2].getLevel());
        E[13]=new edge(4,5,layout[5].getLevel());
        E[14]=new edge(4,8,layout[8].getLevel());
        E[15]=new edge(7,2,layout[2].getLevel());
        E[16]=new edge(7,5,layout[5].getLevel());
        E[17]=new edge(7,8,layout[8].getLevel());

        E[18]=new edge(2,9,4);
        E[19]=new edge(5,9,4);
        E[20]=new edge(8,9,4);

        int levelAt0=layout[0].getLevel();
        int levelAt3=layout[3].getLevel();
        int levelAt6=layout[6].getLevel();

        if(levelAt0<=levelAt3 && levelAt0<=levelAt6)
            optimalPath=bellmanFord(0,9);
        else if(levelAt3<=levelAt0 && levelAt3<=levelAt6)
            optimalPath=bellmanFord(3,9);
        else
            optimalPath=bellmanFord(6,9);

       /* ////////For Debugging///////////
        for(int i=0; i<4; i++)
            System.out.print(optimalPath[i]+" ");
        System.out.print("\n");
        //////////////////////////////// */
    }

    //Implementing BellmanFord
    private int[] bellmanFord(int s, int d)
    {
        int n=10;
        int m=E.length;

        int[] dist=new int[10];
        int[] parent=new int[10];

        for(int i=0; i<n; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        dist[s]=0;

        for(int i=0; i<n-1; i++)
        {
            boolean changes=false;

            for(int j=0; j<m; j++)
            {
                int u=E[j].src;
                int v=E[j].dest;
                int c=E[j].wt;

                if(u==v)
                    continue;

                if(dist[u]<Integer.MAX_VALUE && dist[v]>dist[u]+c)
                {
                    changes=true;
                    dist[v]=dist[u]+c;
                    parent[v]=u;
                }
            }

            if(!changes)
                break;
        }

        int[] path=new int[4];
        path[3]=d;
        path[2]=parent[d];
        path[1]=parent[path[2]];
        path[0]=s;

        return path;
    }


    public int[] getOptimalPath() {
        return optimalPath;
    }
}
