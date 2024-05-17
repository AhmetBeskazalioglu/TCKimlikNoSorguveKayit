# **TcNoSorgulaveKaydet Application**

This project is a Java application that performs querying and saving operations for the Turkish Republic Identification Number (TC Kimlik No). The application validates the information received from the user through a GUI (Graphical User Interface) and saves it to a database.

## **Features**

- **TC Kimlik No Query**: Validates the TC Kimlik No using the information provided by the user.
- **Database Save**: Saves the validated information to the local database.
- **Record Check**: Checks if the record already exists in the database.
- **GUI**: Performs operations through a user-friendly graphical interface.

## **Technologies Used**

- **Java Swing**: For the user interface.
- **KPSPublic and KPSPublicSoap**: Classes used for TC Kimlik No validation.
- **PostgreSQL**: For database management.
- **Singleton Pattern**: For database connection management.

## **Requirements**

- Java 8 or higher
- JDBC supported database (PostgreSQL is used in this example)
- **`tr.gov.nvi.tckimlik.ws`** library (for TC Kimlik No validation)
