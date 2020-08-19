export class User {
	active: boolean;
	address: string;
	city: string;
	country: string;
	email: string;
	emailVerified: boolean;
	firstName: string;
	id: number;
	lastName: string;
	password: string;
	repeatPass: string;
	phoneNumber: string;
	postalCode: string;
	constructor(args: any = {}) {
		this.active = args.active;
		this.address = args.address;
		this.city = args.city;
		this.country = args.country;
		this.email = args.email;
		this.emailVerified = args.emailVerified;
		this.firstName = args.firstName;
		this.lastName = args.lastName;
		this.password = args.password;
		this.phoneNumber = args.phoneNumber;
		this.postalCode = args.postalCode;
	}
}
