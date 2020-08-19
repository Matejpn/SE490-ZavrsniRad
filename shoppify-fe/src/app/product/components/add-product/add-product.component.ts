import { Component, OnInit, ViewChild } from '@angular/core';
import { CategoryService } from 'src/app/shared/services/category.service';
import { BrandService } from 'src/app/shared/services/brand.service';
import { SizeService } from 'src/app/shared/services/size.service';
import { GenderEnum } from 'src/app/core/enums/gender.enum';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { ProductService } from 'src/app/shared/services/product.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'app-add-product',
	templateUrl: './add-product.component.html',
	styleUrls: [ './add-product.component.scss' ]
})
export class AddProductComponent implements OnInit {
	categories = [];
	brands = [];
	sizes = [];
	selectedBrand = null;
	selectedCategory = null;
	genders = Object.keys(GenderEnum);
	selectedGender = null;
	productForm?: FormGroup;
	productSizesArr;

	constructor(
		private readonly categoryService: CategoryService,
		private readonly brandService: BrandService,
		private readonly sizeService: SizeService,
		private fb: FormBuilder,
		private readonly productService: ProductService,
		private router: Router,
		private readonly toastr: ToastrService
	) {
		this.productForm = this.fb.group({
			category: {},
			brand: {},
			gender: '',
			productSizes: this.fb.array([]),
			price: null,
			name: ''
		});
	}

	ngOnInit(): void {
		this.getCategories();
		this.getBrands();
	}

	productSizes(): FormArray {
		this.productSizesArr = this.productForm.get('productSizes') as FormArray;
		return this.productSizesArr;
	}

	newProductSize(): FormGroup {
		return this.fb.group({
			count: 0,
			size: '',
			sizeId: 0
		});
	}

	addProductSize() {
		this.productSizes().push(this.newProductSize());
	}

	removeProductSize(i: number) {
		this.productSizes().removeAt(i);
	}

	getCategories() {
		this.categoryService.getAllCategories().subscribe((res) => {
			this.categories = res;
		});
	}

	onCategorySelect(category) {
		this.selectedCategory = category;
		if (category) {
			this.getSizes(category.name);
		}
	}

	getBrands() {
		this.brandService.getAllBrands().subscribe((res) => {
			this.brands = res;
		});
	}

	getSizes(type: string) {
		this.sizeService.getSizes(type).subscribe((res) => (this.sizes = res));
	}

	onSubmit() {
		const mapSizes = this.productSizesArr.value.map((size) => ({
			count: size.count,
			sizeId: size.size.id
		}));
		const payload = {
			brandId: this.productForm.value.brand.id,
			categoryId: this.productForm.value.category.id,
			gender: this.productForm.value.gender,
			name: this.productForm.value.name,
			price: this.productForm.value.price,
			sizes: mapSizes
		};

		if (!payload.sizes.length) {
			this.toastr.error('Please add sizes!');
		} else if (this.productForm.status === 'VALID') {
			this.productService.addProduct(payload).subscribe((res) => {
				if (res) {
					this.toastr.success('Successfully added product');
					this.router.navigate([ '/product/single', res.id ]);
				}
			});
		} else {
			this.toastr.error('Please fill out the form');
		}
	}
}
