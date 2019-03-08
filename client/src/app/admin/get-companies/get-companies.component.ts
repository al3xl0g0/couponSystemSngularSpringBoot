import {Component, OnInit} from '@angular/core'
import {MatSnackBar, MatTableDataSource} from '@angular/material'
import {Company} from "../../models/company"
import {AdminService} from "../../shared/services/admin.service"
import {animate, state, style, transition, trigger} from '@angular/animations'
import {FormControl, FormGroup, Validators} from "@angular/forms"
import {MyErrorStateMatcher} from "../../models/errorStateMatcher"

@Component({
  selector: 'app-get-companies',
  templateUrl: './get-companies.component.html',
  styleUrls: ['../../../app/shared/layouts/admin/admin.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class GetCompaniesComponent implements OnInit {

  companyColumns: string[] = ['name', 'password', 'email']
  companySource: MatTableDataSource<Company>
  expandedElement: Company | null

  form: FormGroup
  matcher = new MyErrorStateMatcher()
  showUpdate = false

  constructor(private admin: AdminService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.admin.getAllCompanies().subscribe(comps => this.companySource = new MatTableDataSource(comps),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })

    this.form = new FormGroup({

      name: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ]),

      email: new FormControl(null, [
        Validators.required,
        Validators.email,
        Validators.maxLength(20)
      ]),

      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(20)
      ])

    })
  }

  companyFilter(filterValue: string) {
    this.companySource.filter = filterValue.trim().toLowerCase()
  }

  onDelete(id: number) {
    this.admin.deleteCompany(id).subscribe(
      () => this.snackBar.open('Company successfully removed.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onUpdate(id: number) {
    let company: Company = {
      id: id,
      name: this.form.value.name,
      password: this.form.value.password,
      email: this.form.value.email
    }
    this.admin.updateCompany(company).subscribe(
      () => this.snackBar.open('Company successfully updated.', '', {duration: 3000}),
      error => {
        this.snackBar.open(error, '', {duration: 3000})
      })
  }

  onShowUpdate() {
    this.showUpdate === false ? this.showUpdate = true : this.showUpdate = false
  }

}
