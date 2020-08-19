import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class CategoryService {
	private allCategoriesUrl = environment.baseUrl + '/category/categories';

	constructor(private http: HttpClient) {}

	getAllCategories(): Observable<any> {
		return this.http.get<any>(this.allCategoriesUrl);
	}
}
