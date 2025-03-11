import java.util.ArrayList;

public class main {

/*      String nome;
        int rg;
        char sex;
        float altura; */

    public static void main(String[] args) {

        System.out.println("hello world");

        Pessoa p1 = new Pessoa("thiago", 1, 'M', 173);

        Pessoa p2 = new Pessoa("kezya", 2, 'F', 159);

        Pessoa p3 = new Pessoa("vini", 3, 'M', 170);

    ArrayList <Pessoa> aragenda = new ArrayList<>();
        aragenda.add(p1);    
        aragenda.add(p2); 
        aragenda.add(p3); 

        for(int i = 0; i < aragenda.size(); i++)
        {
            System.out.println(aragenda.get(i).getNome());
            System.out.println(aragenda.get(i).getrg());
            System.out.println();
        }

        /*System.out.println(p1.nome);
        System.out.println(p1.rg);
        System.out.println(p1.sex);
        System.out.println(p1.altura);
        
        System.out.println(p1.getNome());

        p1.setrg(123);
        
        System.out.println(p1.getrg());
        System.out.println("adios");

        System.out.println(p2.getNome());
        System.out.println(p2.getrg());

        System.out.println(p3.getNome());
        System.out.println(p3.getrg());*/
    }
}
