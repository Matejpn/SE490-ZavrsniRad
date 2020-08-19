import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class OrderService {
	private getAllUrl = environment.baseUrl + '/purchase/all';
	private getMyUrl = environment.baseUrl + '/purchase/my';

	constructor(private http: HttpClient) {}

	getAll(): Observable<any> {
		return this.http.get<any>(this.getAllUrl);
	}

	getMy(): Observable<any> {
		return this.http.get<any>(this.getMyUrl);
	}
}
