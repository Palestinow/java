import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        
        Carro car1 = new Carro("fiesta", 2012, 50);
        
        Carro car2 = new Carro("celta", 2010, 40);

        Carro car3 = new Carro("uno", 2005, 140);
    
        Carro car4 = new Carro("civic", 2021, 90);
    

        
        ArrayList <Carro> arcars = new ArrayList<>();
            arcars.add(car1);    
            arcars.add(car2); 
            arcars.add(car3);
            arcars.add(car4);

    for(int i = 0; i < arcars.size(); i++)
    {   
        System.out.println();
        System.out.println();
        System.out.println(arcars.get(i).getmodelo());
        System.out.println(arcars.get(i).getano());
        arcars.get(i).exibirvelocidade();

        arcars.get(i).acelerar();
        arcars.get(i).acelerar();
        arcars.get(i).acelerar();
        arcars.get(i).acelerar();
        arcars.get(i).frear();
    } 
    }
        

}
