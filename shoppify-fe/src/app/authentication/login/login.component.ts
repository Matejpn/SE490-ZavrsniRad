import { Component, OnInit, ViewChild } from "@angular/core";
import { AuthenticationService } from "src/app/core/services/authentication.service";
import { Router } from "@angular/router";
import { NgForm } from "@angular/forms";
import { RoleEnum } from "../../core/enums/role.enum";
import { ToastrService } from "ngx-toastr";
import { WelcomeSideComponent } from "src/app/dashboard/components/partials/welcome-side/welcome-side.component";
import { LocalStorageService } from "src/app/shared/services/local-storage.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  emailInput: string;
  passwordInput: string;
  role;
  constructor(
    private auth: AuthenticationService,
    private router: Router,
    private toastr: ToastrService,
    private localStorageService: LocalStorageService
  ) {}

  @ViewChild("loginForm", { static: false })
  loginForm: NgForm;

  @ViewChild(WelcomeSideComponent, { static: false })
  welcomeSide: WelcomeSideComponent;

  ngOnInit() {}

  onSubmit() {
    localStorage.clear();
    if (!this.emailInput || !this.passwordInput) {
    } else {
      this.auth.loginUser(this.emailInput, this.passwordInput).subscribe(
        (res) => {
          localStorage.setItem("token", res.token);
          localStorage.setItem("email", res.username);
          localStorage.setItem("id", res.id);
          localStorage.setItem("role", res.authorities[0].authority);

          this.role = this.auth.getUserRole();
          this.toastr.success("You just successfully login");
          this.localStorageService.roleChanged(res.authorities[0].authority);
          this.router.navigate(["home"]);
        },
        (err) => this.toastr.error(err.error)
      );
    }
  }
}
