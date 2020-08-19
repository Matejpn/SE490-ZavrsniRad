import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { BrandService } from 'src/app/shared/services/brand.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.scss']
})
export class AddItemComponent implements OnInit {
  addBrands;
  @ViewChild("brandForm", { static: false })
  brandForm: NgForm;
  constructor(
    private route: ActivatedRoute,
    private readonly brandService: BrandService,
    private readonly toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(res => {
      if (res.item === 'brands') {
        this.addBrands = true;
      }
    })
  }

  addBrand() {
    this.brandService.addBrand(this.brandForm.value.brand)
      .subscribe(res => {
        if (res) {
          this.toastr.success("You just successfully added a brand");
          this.brandForm.reset();
        } else {
          this.toastr.error("Something went wrong");

        }
      }
      )

  }

}
