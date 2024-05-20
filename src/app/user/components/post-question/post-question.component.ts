import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, inject } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import {ENTER, COMMA } from '@angular/cdk/keycodes'
import { LiveAnnouncer } from '@angular/cdk/a11y';
import {MatChipInputEvent, MatChipEditedEvent} from '@angular/material/chips'
import { MatSnackBar } from '@angular/material/snack-bar';

export interface Tags {
  name: string;
}

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrl: './post-question.component.scss',

})
export class PostQuestionComponent {
  tags:Tags[]=[];
  isSubmitting!: boolean;
  addOnBlur=true;
  validateForm!: FormGroup;

  readonly separatorKeysCodes=[ENTER, COMMA] as const;
  announcer=inject(LiveAnnouncer);

  add(event: MatChipInputEvent): void{
    const value=(event.value ||  '').trim();
    //add our tags
    if(value)
    {
      this.tags.push({name: value});
    }
    //clear input value
    event.chipInput!.clear();
  }

  remove(tag: any):void{
    const index=this.tags.indexOf(tag);
    if(index>=0)
    {
      this.tags.splice(index,1);
      this.announcer.announce(`Removed ${tag}`);
    }
  }

  edit(tag: any, event: MatChipEditedEvent){
    const value=event.value.trim();
    //remove tag if it no longer has a name
    if(!value)
    {
      this.remove(tag);
      return;
    }
    //edit existing tag
    const index=this.tags.indexOf(tag);
    if(index>=0)
    {
      this.tags[index].name=value;
    }
  }

  constructor(private service: QuestionService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar){}

  ngOnInit(){
    this.validateForm=this.fb.group({
      title: ['',Validators.required],
      body: ['',Validators.required],
      tags: ['',Validators.required]
    })
  }

  postQuestion(){
    console.log(this.validateForm.value);
    this.service.postQuestion(this.validateForm.value).subscribe((res)=>{
      console.log(res);
      if(res.id!=null)
      {
        this.snackBar.open("Question posted successfully", "Close",
        {duration: 5000});
      }
      else{
        this.snackBar.open("Something went wrong", "Close",
        {duration: 5000});
      }
    });
  }
}
