import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CalculatorService} from '../calculator.service';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  mathForm: FormGroup;
  answer = null;
  errorResponse = null;

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
          this.answer = data;
        },
        error => {
          this.errorResponse = error;
          console.log(error);
        });
  }
}
