class Author {
    private String name;

    public Author(String name){
        if (name.isBlank()){
            //throw new IllegalArgumentException("Author must have a name");
            this.name = "";
        }
        else{
            this.name = name;
        }
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    
}
