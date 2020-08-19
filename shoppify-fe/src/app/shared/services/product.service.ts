import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const HttpUploadOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
};
@Injectable({
	providedIn: 'root'
})
export class ProductService {
	private getAllProductsUrl = environment.baseUrl + '/product/all';
	private getByIdUrl = environment.baseUrl + '/product/';
	private addProductUrl = environment.baseUrl + '/product';
	private uploadPhotoUrl = environment.baseUrl + '/product/upload-photo/';
	private removePhotoUrl = environment.baseUrl + '/product/remove-photo/';

	constructor(private http: HttpClient) {}

	getAllProducts(filter: any): Observable<any> {
		return this.http.get<any>(this.getAllProductsUrl, {
			params: filter
		});
	}

	getById(id: Number): Observable<any> {
		return this.http.get<any>(this.getByIdUrl + id);
	}

	addProduct(product): Observable<any> {
		return this.http.post(this.addProductUrl, product);
	}

	uploadPhoto(id: number, formData): Observable<any> {
		return this.http.post(`${this.uploadPhotoUrl}${id}`, formData, HttpUploadOptions);
	}

	removePhoto(id: number): Observable<any> {
		return this.http.delete(`${this.removePhotoUrl}${id}`, {});
	}
}
