package mylotte_backend

class Category {
    String name
    static hasMany = [products: Product]

    static constraints = {
    }
}
