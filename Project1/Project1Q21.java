import java.math.BigInteger;

public class Project1Q21 {
    public static void main(String[] args) {
        BigInteger a1 = new BigInteger("7000");
        BigInteger a2 = new BigInteger("7294");
        BigInteger b1 = new BigInteger("25");
        BigInteger b2 = new BigInteger("5038385");
        BigInteger c1 = new BigInteger("-59724");
        BigInteger c2 = new BigInteger("783");
        BigInteger d1 = new BigInteger("8516");
        BigInteger d2 = new BigInteger("-82147953548159344");
        BigInteger e1 = new BigInteger("45952456856498465985");
        BigInteger e2 = new BigInteger("98654651986546519856");
        BigInteger f1 = new BigInteger("-45952456856498465985");
        BigInteger f2 = new BigInteger("-98654651986546519856");
        Ala_Carte_Multiplication(a1, a2);
        Ala_Carte_Multiplication(b1, b2);
        Ala_Carte_Multiplication(c1, c2);
        Ala_Carte_Multiplication(d1, d2);
        Ala_Carte_Multiplication(e1, e2);
        Ala_Carte_Multiplication(f1, f2);
    }

    public static void Ala_Carte_Multiplication(BigInteger a, BigInteger b){
        BigInteger c = a.abs();
        BigInteger d = b.abs();
        BigInteger e = new BigInteger("2");
        BigInteger f = new BigInteger("1");
        BigInteger g = new BigInteger("0");
        BigInteger h = g;
        int i = a.compareTo(g);
        int j = b.compareTo(g);
        if(c.compareTo(d) < 0){
            while(!c.equals(h)){
                if(c.mod(e).equals(f)){
                    g = g.add(d);
                }
                c = c.divide(e);
                d = d.multiply(e);
            }
        } else {
            while(!d.equals(h)){
                if(d.mod(e).equals(f)){
                    g = g.add(c);
                }
                d = d.divide(e);
                c = c.multiply(e);
            }
        }

        if((i > 0 && j > 0) || (i < 0 && j < 0)){
            System.out.println(a + " * " + b + " = " + g);
        } else {
            System.out.println(a + " * " + b + " = " + "-" + g);
        }
    }
}
