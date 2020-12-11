import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Expression} from './model/expression';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CalculatorService {

  constructor(private http: HttpClient) {
  }

  public getResult(integer1: number, integer2: number, operatorValue: string): Observable<any> {
    const operator = this.getOperator(operatorValue);
    return this.http.get<any>(`/api/v1/calculate?integer1=${integer1}&integer2=${integer2}&operator=${operator}`, httpOptions);
  }

  public postCalculationList(expressions: Expression[]): Observable<any> {
    return this.http.post('/api/v1/calculatelist', expressions, httpOptions);
  }

  public getResults(): Observable<any> {
    return this.http.get<any>(`/api/v1/calculations`);
  }

  public getOperator(operator: string): string {
    switch (operator) {
      case '+': {
        return 'ADDITION';
      }
      case '-': {
        return 'SUBTRACTION';
      }
      case '*': {
        return 'MULTIPLICATION';
      }
      case '/': {
        return 'DIVISION';
      }
    }
  }

}
