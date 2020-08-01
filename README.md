# search

I created this simple module to assist with the search functionality in Java applications.

There is usually a need to search/filter data presented in a UI element, across software platforms.

In my case, I usually need to filter/search data presented in an Android ListView(Android) or in a JList/JTable(Java Swing Desktop).

How do we go about this?

1. The immediately outstanding part of the problem is that the data we are trying to present is as varied as the diversity of the possible reference types.
2. The data we are trying to match this against is usually textual string from an EditText(Android) , a SearchView(Android) , a JTextField (Java Swing) or any Java text component.

This then drives our design principles.

We create an abstract model that can give us facilities to search everything that is searchable, and make it generic in what it can search.

Our goal is to have a single class that uses base Java constructs so that it can work across all Java platforms, unchanged.









