package com.company;


import java.util.Map;
        import java.util.HashMap;
        import java.util.ArrayList;
        import java.util.Random;
        import java.util.Iterator;

class l1{
    enum Color{
        WHITE,
        BLUE,
        GREEN,
        PURPLE,
        YELLOW,
        ORANGE
    }

    enum Nominal{
        ONE(1, Color.WHITE),
        TEN(10, Color.BLUE),
        TWENTY(20, Color.GREEN),
        FIFTY(50, Color.PURPLE),
        HUNDREED(100, Color.YELLOW),
        FIVEHUNDREED(500, Color.ORANGE);
        public int val;
        public Color color;
        Nominal(int value, Color c){
            val = value;
            color = c;
        }
    }

    static class NotRealBank{
        private Map<Nominal, Integer> banknotes;
        NotRealBank(){
            banknotes = new HashMap<Nominal, Integer>();
        }

        public void addBanknotes(Nominal n, int quantity){
            if(banknotes.containsKey(n)){
                banknotes.replace(n, banknotes.get(n) + quantity);
            }else{
                banknotes.put(n, quantity);
            }
        }

        public Integer getQuantity(Nominal n){
            if(banknotes.containsKey(n)){
                return banknotes.get(n);
            }else{
                return 0;
            }
        }

        public void showBank(){
            for(Map.Entry<Nominal, Integer> entry : banknotes.entrySet()){
                System.out.println("Nominal: " + entry.getKey().val + "\nBanknotes in this bank: " + entry.getValue());
            }
        }
    }

    abstract static class Card{
        private String title;
        private String description;
        private String effect;
        Card(String t, String d, String e){
            title = t;
            description = d;
            effect = e;
        }
        public String getTitle(){
            return title;
        }
        public String getDescription(){
            return description;
        }
        public String getEffect(){
            return effect;
        }
        public abstract void printInfo();
        public abstract void action();
    }

    static class Monopoly{
        private NotRealBank bank;
        private Random rnd;
        class ChanceCard extends Card{
            ChanceCard(String t, String d, String e){
                super(t, d, e);
            }
            public void printInfo(){
                System.out.println("Your chance is here!\n" + getTitle() + "\n" + getDescription());
            }
            public void action(){
                System.out.println("Well, now you have to " + getEffect());
            }
        }
        class PropertyCard extends Card{
            private Integer price;
            private Integer gain;
            PropertyCard(String t, String d, String e, Integer price, Integer gain){
                super(t, d, e);
                this.price = price;
                this.gain = gain;
            }
            public void printInfo(){
                System.out.println("This is the property:\n" + getTitle() + "\n" + getDescription());
            }
            public void action(){
                System.out.println("You can buy " + getTitle() + " for a fair price of " + price + ", gain here equals " + gain + " when " + getEffect());
            }
        }

        private ArrayList<ChanceCard> chances;
        private ArrayList<PropertyCard> properties;

        private void bankToDefault(){
            bank = new NotRealBank();
            bank.addBanknotes(Nominal.ONE, 100);
            bank.addBanknotes(Nominal.TEN, 100);
            bank.addBanknotes(Nominal.TWENTY, 100);
            bank.addBanknotes(Nominal.FIFTY, 50);
            bank.addBanknotes(Nominal.HUNDREED, 50);
            bank.addBanknotes(Nominal.FIVEHUNDREED, 20);
        }

        Monopoly(){
            bankToDefault();
            rnd = new Random();

            chances = new ArrayList<ChanceCard>();
            chances.add(new ChanceCard("A", "Taxes!", "pay 150."));
            chances.add(new ChanceCard("B", "Rent!", "pay 200."));
            chances.add(new ChanceCard("C", "Competition!", "gain 10."));
            chances.add(new ChanceCard("D", "Fine!", "pay 20."));
            chances.add(new ChanceCard("E", "Salary!", "gain 500."));

            properties = new ArrayList<PropertyCard>();
            properties.add(new PropertyCard("pA", "A street.", "somebody walks", 200, 3));
            properties.add(new PropertyCard("pB", "A house.", "somebody stays", 500, 50));
            properties.add(new PropertyCard("pC", "A library.", "somebody reads", 300, 10));
            properties.add(new PropertyCard("pD", "A shop.", "somebody buys", 1000, 100));
            properties.add(new PropertyCard("pE", "A hotel.", "somebody sleeps", 2000, 250));
        }

        public void getRandomChance(){
            int ind = rnd.nextInt(chances.size());
            chances.get(ind).printInfo();
            chances.get(ind).action();
        }

        public void getRandomProperty(){
            int ind = rnd.nextInt(properties.size());
            properties.get(ind).printInfo();
            properties.get(ind).action();
        }

        public void getAvailableChances(){
            Iterator<ChanceCard> iter = chances.iterator();
            while(iter.hasNext()){
                System.out.println(iter.next().getTitle());
            }
        }

        public void getAvailableProperties(){
            Iterator<PropertyCard> iter = properties.iterator();
            while(iter.hasNext()){
                System.out.println(iter.next().getTitle());
            }
        }

        public NotRealBank getBank(){
            return bank;
        }
    }


    public static void main(String[] args){
        Monopoly game = new Monopoly();
        System.out.println("Lets start with all available Chances:");
        game.getAvailableChances();
        System.out.println("Lets get one random Chance Card:");
        game.getRandomChance();
        System.out.println("Lets walk through all available Properties:");
        game.getAvailableProperties();
        System.out.println("Now well see one random Property:");
        game.getRandomProperty();
        System.out.println("What about banknotes in the Bank?");
        game.getBank().showBank();
    }
}