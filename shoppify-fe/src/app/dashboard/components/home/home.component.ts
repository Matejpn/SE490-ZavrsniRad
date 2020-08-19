import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/shared/services/product.service';
import { CategoryService } from 'src/app/shared/services/category.service';
import { BrandService } from 'src/app/shared/services/brand.service';

import * as moment from 'moment';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: [ './home.component.scss' ]
})
export class HomeComponent implements OnInit {
	products = [];
	categories = [];
	brands = [];
	filter: any = {};
	searchTerm: String = '';
	showNoProducts = false;
	orderBy = 'releseDateDesc';
	minPrice: any;
	maxPrice: any;
	dateFrom: any;
	dateTo: any;

	constructor(
		private productService: ProductService,
		private categoryService: CategoryService,
		private brandService: BrandService
	) {}

	ngOnInit(): void {
		this.getCategories();
		this.getBrands();
	}

	selectCategory(id) {
		this.filter = {
			...this.filter,
			idCategory: id
		};
		this.getProducts();
	}

	selectBrand(id: any) {
		if (id == 0) {
			delete this.filter.idBrand;
		} else {
			this.filter = {
				...this.filter,
				idBrand: id
			};
		}
		this.getProducts();
	}

	changeMinPrice(e: any) {
		const { value } = e.target;
		if (value) {
			this.filter = {
				...this.filter,
				priceFrom: value
			};
		} else {
			delete this.filter.priceFrom;
		}
		this.getProducts();
	}

	changeMaxPrice(e: any) {
		const { value } = e.target;
		if (value) {
			this.filter = {
				...this.filter,
				priceTo: value
			};
		} else {
			delete this.filter.priceTo;
		}
		this.getProducts();
	}

	changeDateFrom(e: any) {
		const { value } = e.target;
		if (value) {
			this.filter = {
				...this.filter,
				dateFrom: value
			};
		} else {
			delete this.filter.dateFrom;
		}
		this.getProducts();
	}

	changeDateTo(e: any) {
		const { value } = e.target;
		if (value) {
			this.filter = {
				...this.filter,
				dateTo: value
			};
		} else {
			delete this.filter.dateTo;
		}
		this.getProducts();
	}

	refreshFilterParams() {
		this.minPrice = null;
		this.maxPrice = null;
		this.dateFrom = null;
		this.dateTo = null;

		delete this.filter.idBrand;
		delete this.filter.priceFrom;
		delete this.filter.priceTo;
		delete this.filter.dateFrom;
		delete this.filter.dateTo;
		this.getProducts();
	}

	getProducts() {
		this.productService.getAllProducts(this.filter).subscribe((res) => {
			this.products = res.filter((p) => {
				return p.name.toLowerCase().includes(this.searchTerm.toLowerCase());
			});
			this.showNoProducts = !(this.products.length > 0);
		});
	}

	getCategories() {
		this.categoryService.getAllCategories().subscribe((res) => {
			this.categories = res;
			this.filter = {
				...this.filter,
				idCategory: res[0].id
			};
			this.getProducts();
		});
	}

	getBrands() {
		this.brandService.getAllBrands().subscribe((res) => {
			this.brands = res;
		});
	}

	orderChanged(value) {
		this.orderBy = value;
		this.sortItems(value);
	}

	sortItems(value) {
		if (value === 'priceAsc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersAsc(a, b, 'price');
			});
		} else if (value === 'priceDesc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersDesc(a, b, 'price');
			});
		} else if (value === 'popularityAsc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersAsc(a, b, 'popularity');
			});
		} else if (value === 'popularityDesc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersDesc(a, b, 'popularity');
			});
		} else if (value === 'releseDateAsc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersAsc(a, b, 'releseDate');
			});
		} else if (value === 'releseDateDesc') {
			this.products = this.products.sort((a, b) => {
				return this.compareNumbersDesc(a, b, 'releseDate');
			});
		}
	}

	compareNumbersAsc(a, b, path) {
		if (a[path] < b[path]) {
			return -1;
		}
		if (a[path] > b[path]) {
			return 1;
		}
		return 0;
	}

	compareNumbersDesc(a, b, path) {
		if (a[path] > b[path]) {
			return -1;
		}
		if (a[path] < b[path]) {
			return 1;
		}
		return 0;
	}

	searchChanged() {
		this.getProducts();
	}
}
