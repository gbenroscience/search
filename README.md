# search

I created this simple module to assist with the search functionality in Java applications.

There is usually a need to search/filter data presented in a UI element, across software platforms.

In my case, I usually need to filter/search data presented in an Android ListView(Android) or in a JList/JTable(Java Swing Desktop).

How do we go about this?

1. The immediate outstanding part of the problem is that the data we are trying to present is as varied as the diversity of the possible reference types.
2. The data we are trying to match this against is usually textual string from an EditText(Android) , a SearchView(Android) , a JTextField (Java Swing) or any Java text component.

This then drives our design principles.

We create an abstract model that can give us facilities to search everything that is searchable, and make it generic in what it can search.

Our goal is to have a single class that uses base Java constructs so that it can work across all Java platforms, unchanged.

The resulting product, Search.java represents everything that a search/filtering model should be able to do.

To use it, say you have a Person class:

```java
public class Person {

    private String firstName;
    private String lastName;
    private int age;

    public Person(String fName, String lName, int age) {
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
```

Now say you have loaded a list of Person objects into a JList, a JTable, a GridView, a ListView or a RecyclerView or other; to make your UI element searchable, create a Search object.

```java
        Search search = new Search<Person>() {
            @Override
            public void update(ArrayList<Person> data) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        PersonTableModel model = getModel();
                        model.setData(search.getData());
                    }
                });

            }

            @Override
            public boolean match(String searchText , Person p) {
               
                String searchKey = searchText.toLowerCase();

                
                        String fname = p.getFirstName().toLowerCase();
                        String lname = p.getLastName().toLowerCase();
                        String age =      String.valueOf(p.getAge());
                        
                        

                        return  (  fname.equals(searchKey) || fname.contains(searchKey)
                                || lname.equals(searchKey) || lname.contains(searchKey)
                                || age.equals(searchKey) || age.contains(searchKey)   );
                    
                
            }
        };
```

In the implemented 
```java
update(ArrayList)
``` 
method , set the updated data(the search results) to your UI's view model. (e.g BaseAdapter in Android, TableModel or ListModel
in Java swing)

In the implemented 
```java
match(String  , Person)
``` 
method, simply specify the search criteria: this will usually be a relationship between the search text and the properties of the items in the UI's view model.

At the instance of loading , at the point in your code where you load data into your ui model, call
```java
search.resetData(data);
```

Then at the point in your code where you detect changes in your UI's search element:
Pass the updated text to the search object.

```java
search.find(newTxt);
```

And you are good to go.

This is a powerful generic search technique whose algorithm can be easily translated into other languages, e.g Swift for iOS, javascript for web frontends etc.










