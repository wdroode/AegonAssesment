import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CalculatorService} from '../calculator.service';
import {Expression} from '../model/expression';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  mathForm: FormGroup;
  errorResponse = null;
  calcList = [];
  answers = [];
  results = [];

  operatorList: string[] = ['+', '-', '*', '/'];

  constructor(
    private formBuilder: FormBuilder,
    private calculatorService: CalculatorService
  ) {
    this.mathForm = this.formBuilder.group({
      integer1: ['', [Validators.pattern('^[0-9]'), Validators.required]],
      integer2: ['', [Validators.pattern('^[0-9]'), Validators.required]],
      operator: ['', [Validators.pattern('^[-+/*]'), Validators.required]]
    });
  }

  ngOnInit(): void {
    this.getResults();
  }

  get integer1(): AbstractControl {
    return this.mathForm.get('integer1');
  }

  get integer2(): AbstractControl {
    return this.mathForm.get('integer2');
  }

  get operator(): AbstractControl {
    return this.mathForm.get('operator');
  }

  calculate(): void {
    this.calculatorService.getResult(this.integer1.value, this.integer2.value, this.operator.value)
      .subscribe(
        data => {
          this.answers = data.result.results;
        },
        error => {
          this.errorResponse = error;
          console.log(error);
        });

    this.getResults();
  }

  calculateList(): void {
    this.calculatorService.postCalculationList(this.calcList).subscribe(
      data => {
        console.log(data.result);
        this.answers = data.result.results;
      },
      error => {
        this.errorResponse = error;
        console.log(error);
      });

    this.calcList = [];
    this.getResults();
  }

  addToList(): void {
    this.calcList.push(new Expression(this.integer1.value, this.integer2.value, this.calculatorService.getOperator(this.operator.value)));
  }

  getResults(): void {
    this.calculatorService.getResults().subscribe(
      data => {
        this.results = data.result;
      },
      error => {
        this.errorResponse = error;
        console.log(error);
      });
  }
}
