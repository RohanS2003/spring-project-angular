import { Component } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm!: FormGroup;

  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ){}

  ngOnInit(){
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      confirmPassword: ['', Validators.required],
    }, {validate: this.confirmationValidator});

  }

  private confirmationValidator(fg: FormGroup)
  {
    const password=fg.get('password')?.value;
    const confirmPassword=fg.get('confirmPassowrd')?.value;
    if(password!=confirmPassword)
    {
      fg.get('confirmPassword')?.setErrors({passwordMismatch: true});
    }
    else{
      fg.get('confirmPassword')?.setErrors(null);
    }
  }

  signup(){
    console.log(this.signupForm.value);
    this.service.signup(this.signupForm.value).subscribe((response) => {
      console.log(response);
      if(response.id!=null)
      {
        this.snackBar.open(
          "Registered Successfully",
          'Close',
          {duration: 5000}
        );
          this.router.navigateByUrl('/login');
      }
      else{
        this.snackBar.open(response.message,
          'Close',
          {duration: 5000})
      }
    }, (error: any)=>{
      this.snackBar.open(
        "Registeration failed",
        'Close',
        {duration: 5000}
      )
    })
  }
}
