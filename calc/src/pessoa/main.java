package pessoa;
public class main {

/*      String nome;
        int rg;
        char sex;
        float altura; */

    public static void main(String[] args) {

        System.out.println("hello world");

        Pessoa p = new Pessoa("thiago", 1, 'M', 173);

        System.out.println(p.nome);
        System.out.println(p.rg);
        System.out.println(p.sex);
        System.out.println(p.altura);
        
        System.out.println(p.getNome());

        p.setrg(123);
        
        System.out.println(p.getrg());
        System.out.println("adios");
    }
}
