package mylotte_backend

class Product {
    String name
    String manufacturer
    Float measure
    String measureType
    static hasMany = [categories: Category, packings: Packing]
    static belongsTo = [Category]
    static constraints = {
    }
}
