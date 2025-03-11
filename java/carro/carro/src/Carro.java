public class Carro {
    String modelo;
    int ano;
    float velocidadeatual;

Carro(String modelo, int ano, float velocidadeatual)
    {
    this.modelo = modelo;
    this.ano = ano;
    this.velocidadeatual = velocidadeatual;        
    }

    public String getmodelo() {
        return modelo;
    }

    public int getano() {
        return ano;
    }

    public float getvelocidadeatual() {
        return velocidadeatual;
    }

public void acelerar()
{
    System.out.println("Aceleraaaaaa");
    this.velocidadeatual += 10;
    System.out.println("Velocidade atual:");
    System.out.println(velocidadeatual);
}

public void frear()
{
    System.out.println("freiaaaaa");
        if(velocidadeatual >= 10)
        {
        velocidadeatual = velocidadeatual - 10;
        }
        else
        {
        System.out.println("Velocidade minima = 0");
        }
    System.out.println("Velocidade atual:");
    System.out.println(velocidadeatual);
}

public void exibirvelocidade()
{
    System.out.println("Velocidade atual:");
}
}

