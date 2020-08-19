import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/shared/services/product.service';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'app-upload-photo',
	templateUrl: './upload-photo.component.html',
	styleUrls: [ './upload-photo.component.scss' ]
})
export class UploadPhotoComponent implements OnInit {
	@ViewChild('inputRef') inputRef;
	product: any = {};
	uploadedPicture: any;
	fileValid = false;
	constructor(private route: ActivatedRoute, private productService: ProductService, private toastr: ToastrService) {}

	ngOnInit(): void {
		this.getProduct();
	}

	getProduct() {
		this.productService.getById(Number(this.route.snapshot.paramMap.get('id'))).subscribe((res) => {
			this.product = res;
			console.log(res);
		});
	}

	openImage() {
		this.inputRef.nativeElement.click();
	}

	uploadImage() {
		const formData = new FormData();
		formData.append('file', this.uploadedPicture);
		this.productService.uploadPhoto(this.product.id, formData).subscribe(() => {
			this.getProduct();
			this.toastr.success('Successfully uploaded image!');
			this.uploadImage = null;
			this.fileValid = false;
		});
	}

	removeImage(image) {
		this.productService.removePhoto(image.id).subscribe(() => {
			this.product.images = this.product.images.filter((im) => im !== image);
		});
	}

	onFileSelect(event) {
		const files = event.target.files;
		this.uploadedPicture = files[0];
		this.validateFile();
	}

	validateFile() {
		if (!this.uploadedPicture) {
			this.fileValid = false;
			return false;
		} else if (
			this.uploadedPicture.type !== 'image/jpeg' &&
			this.uploadedPicture.type !== 'image/jpg' &&
			this.uploadedPicture.type !== 'image/png'
		) {
			this.toastr.error('Wrong format');
			this.fileValid = false;
			return false;
		} else if (this.uploadedPicture.size >= 5242880) {
			this.toastr.error('Size too big');
			this.fileValid = false;
			return false;
		} else {
			this.fileValid = true;
			return true;
		}
	}
}
