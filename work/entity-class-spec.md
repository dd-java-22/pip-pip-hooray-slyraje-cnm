# Entity classes: 5 points
For every entity in the ERD, there must be a corresponding entity class in your implementation project. There must not be any entity class in the implementation project that does not correspond to an entity in the ERD.

## Organization
- All of the entity classes must be located either in the model or model.entity subpackage (within the base package of the project). Note that the choice to place an entity class in an entity subpackage must be made consistently: Please don’t put one entity class in the model.entity package, while putting another entity class from the same project directly in the model package.

- Entity classes must be linked to from the GitHub Pages site. That is, entity classes must be listed in the GitHub Pages-based documentation, and each must be displayed as a link to the source code in GitHub for that class.

## ERD-to-class correspondence
- Each entity class must be annotated with @Entity.

- The tableName attribute of the @Entity annotation must be used to specify a lower_snake_case name for the corresponding table; this name must match (exactly) the name of the corresponding entity in the ERD. For example, an entity class named MusicGenre, corresponding to an entity shown in the ERD as music_genre could be declared as follows:

> @Entity(tableName = "music_genre")\
> public class MusicGenre {...}


## Attribute-to-field correspondence
- Every attribute listed in the ERD must be present as a field in the associated entity class. Aside from these attribute-related fields, there must not be any other non-static, non-transient, non-@Ignore-annotated fields in the entity class.

- All fields must be private, and named in lowerCamelCase.

- All fields corresponding to entity attributes must have both accessors and mutators (i.e., getters and setters).

- If the entity’s primary key is composed of a single attribute, then the corresponding field must be named id, with a type of long, and it must be annotated with @PrimaryKey(autoGenerate = true).

Such a field must also use the name element of the @ColumnInfo annotation to specify a column name consisting of the entity name, followed by “id”, expressed in lower_snake_case. For example, a field corresponding to the primary key attribute of an entity class named DevelopmentTeam would be declared as follows:

> @PrimaryKey(autoGenerate = true)\
> @ColumnInfo(name = "development_team_id")\
> private long id;

## Field names must correspond to the attribute names. If a field’s lowerCamelCase name consists of multiple words, the name attribute of the @ColumnInfo annotation must be used to specify the column name in lower_snake_case; that column name must match the attribute name in the ERD. For example, a long field named managerId would be declared as follows:

>@ColumnInfo(name = "manager_id")\
>private long managerId;\

- The field types must correspond to those given in the ERD (i.e., they must be the same as Java types in the ERD, or must be the Java equivalents of SQLite types in the ERD).

- Any field in an entity class corresponding to a non-nullable attribute in the ERD must either be declared as a primitive type or be annotated with @NonNull to enforce that constraint.

- Any field in an entity class corresponding to a nullable attribute in the ERD must be declared as an object (not primitive) type, and must not be annotated with @NonNull.

## Other requirements
- Entity classes must use the expected rational ordering of class members: all fields, then constructors (if any), then all methods (including accessors and mutators), and finally nested classes/interfaces/enums (if any).

- Each entity class must include all appropriate class-level and field-level annotations to implement the primary keys, unique constraints, and other indices indicated in the ERD. Important: Don’t forget to create indices for foreign keys! (These are not automatically created by Room or SQLite, as they are in most other databases.)

- If any field of the class is of an enumerated type, that enum must be declared as a top-level enum in the model.pojo or model.type subpackage; it must also include @TypeConverter-annotated public static methods to convert between the enumerated type and Integer.