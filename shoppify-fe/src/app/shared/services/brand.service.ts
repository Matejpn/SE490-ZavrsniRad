import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class BrandService {
	private allBrandsUrl = environment.baseUrl + '/brand/brands';
	private addBrandUrl = environment.baseUrl + '/brand';

	constructor(private http: HttpClient) { }

	getAllBrands(): Observable<any> {
		return this.http.get<any>(this.allBrandsUrl);
	}

	addBrand(name: string) {
		return this.http.post<any>(this.addBrandUrl, { name: name });
	}
}
