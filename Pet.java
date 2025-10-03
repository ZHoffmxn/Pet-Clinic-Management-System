public abstract class Pet {
    protected String name;
    protected int age;
    protected String colour;
    protected double weight;
    protected Owner owner;

    public Pet(String name, int age, String colour, double weight, Owner owner) {
        setName(name);
        setAge(age);
        setColour(colour);
        setWeight(weight);
        setOwner(owner);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 0 || age > 30) {
            throw new IllegalArgumentException("Invalid age. Age must be between 0 and 30.");
        }
        this.age = age;
    }

    public void setColour(String colour) {
        if (colour == null || colour.trim().isEmpty()) {
            throw new IllegalArgumentException("Colour cannot be empty");
        }
        this.colour = colour.trim();
    }

    public void setWeight(double weight) {
        if (weight <= 0 || weight > 300) {
            throw new IllegalArgumentException("Invalid weight. Weight must be positive and less than 300 kg.");
        }
        this.weight = weight;
    }

    public void setOwner(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String speak() {
        return determineNoise() + "! I am " + name + ", a " + age + " year old " + getBreed() +
                " owned by " + owner.getName() + ".";
    }

    protected abstract String determineNoise();

    public abstract String getType();
    public abstract String getBreed();

    @Override
    public String toString() {
        return name + ", " + age + " years old, " + colour + ", " + weight + "kg, Owner: " + owner.getName();
    }

    public static class Cat extends Pet {
        private String breed;

        public Cat(String name, int age, String colour, double weight, String breed, Owner owner) {
            super(name, age, colour, weight, owner);
            setBreed(breed);
        }

        public void setBreed(String breed) {
            if (breed == null || breed.trim().isEmpty()) {
                throw new IllegalArgumentException("Breed cannot be empty");
            }
            this.breed = breed.trim();
        }

        @Override
        protected String determineNoise() {
            return "Miaow";
        }

        @Override
        public String getType() {
            return "Cat";
        }

        @Override
        public String getBreed() {
            return breed;
        }

        @Override
        public String toString() {
            return super.toString() + ", Breed: " + breed;
        }
    }

    public static class Dog extends Pet {
        private String breed;

        public Dog(String name, int age, String colour, double weight, String breed, Owner owner) {
            super(name, age, colour, weight, owner);
            setBreed(breed);
        }

        public void setBreed(String breed) {
            if (breed == null || breed.trim().isEmpty()) {
                throw new IllegalArgumentException("Breed cannot be empty");
            }
            this.breed = breed.trim();
        }

        @Override
        protected String determineNoise() {
            return "Woof";
        }

        @Override
        public String getType() {
            return "Dog";
        }

        @Override
        public String getBreed() {
            return breed;
        }

        @Override
        public String toString() {
            return super.toString() + ", Breed: " + breed;
        }
    }

    public static class Hamster extends Pet {
        private String breed;

        public Hamster(String name, int age, String colour, double weight, String breed, Owner owner) {
            super(name, age, colour, weight, owner);
            setBreed(breed);
        }

        public void setBreed(String breed) {
            if (breed == null || breed.trim().isEmpty()) {
                throw new IllegalArgumentException("Breed cannot be empty");
            }
            this.breed = breed.trim();
        }

        @Override
        protected String determineNoise() {
            return "Squeak";
        }

        @Override
        public String getType() {
            return "Hamster";
        }

        @Override
        public String getBreed() {
            return breed;
        }

        @Override
        public String toString() {
            return super.toString() + ", Breed: " + breed;
        }
    }
}