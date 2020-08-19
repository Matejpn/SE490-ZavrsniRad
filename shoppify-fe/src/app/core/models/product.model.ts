export class Product {
    brandId: number;
    brandName: string;
    categoryId: number;
    categoryName: string;
    genre: string;
    images: [];
    name: string;
    price: number;
    productSizes: [];
    releseDate: string;
    constructor(args: any = {}) {
        this.brandId = args.brandId;
        this.brandName = args.brandName;
        this.categoryId = args.categoryId;
        this.categoryName = args.categoryName;
        this.genre = args.genre;
        this.images = args.images;
        this.name = args.name;
        this.price = args.price;
        this.productSizes = args.productSizes;
        this.releseDate = args.releseDate;
    }
}
