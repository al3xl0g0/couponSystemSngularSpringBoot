import {Injectable} from '@angular/core'
import {HttpClient} from "@angular/common/http"
import {Observable} from "rxjs"
import {User} from "../../models/user"
import {ErrorService} from "./error.service"
import {catchError, tap} from "rxjs/operators"
import {Router} from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class AuthorisationService {

  isLoggedIn = false

  private logUrl = 'http://localhost:8080/coupon-system/login'
  private regCompanyUrl = 'http://localhost:8080/coupon-system/registration/company'
  private regCustomerUrl = 'http://localhost:8080/coupon-system/registration/customer'

  constructor(private http: HttpClient,
              private errorService: ErrorService,
              private router: Router) {
  }

  public login(logUser: User): Observable<User> {
    return this.http.post<User>(this.logUrl, logUser, {withCredentials: true}).pipe(
      tap(() => {
        this.isLoggedIn = true
      }),
      catchError(err => this.errorService.errorHandler(err)))
  }

  public regCompany(regUser: User): Observable<User> {
    return this.http.post<User>(this.regCompanyUrl, regUser, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public regCustomer(regUser: User): Observable<User> {
    return this.http.post<User>(this.regCustomerUrl, regUser, {withCredentials: true}).pipe(
      catchError(err => this.errorService.errorHandler(err)))
  }

  public logout() {
    this.isLoggedIn = false
    this.router.navigate(['/login'])
  }

}
