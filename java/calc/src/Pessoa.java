public class Pessoa 
{
        String nome;
        int rg;
        char sex;
        float altura;

    Pessoa(String nome, int rg, char sex, float altura){
        this.nome = nome;
        this.rg = rg;
        this.sex = sex;
        this.altura = altura;
        
    }
    public String getNome() {
        return nome;
    }
    public int getrg() {
        return rg;
    }
    public void setrg(int rg){
        this.rg = rg;
    }
    
}