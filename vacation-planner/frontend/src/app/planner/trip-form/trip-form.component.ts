import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TripService } from '../../core/services/trip.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-trip-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './trip-form.component.html',
  styleUrls: ['./trip-form.component.scss']
})
export class TripFormComponent implements OnInit {
  tripForm: FormGroup;
  tripId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private tripService: TripService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.tripForm = this.fb.group({
      title: ['', Validators.required],
      destination: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      budget: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.tripId = this.route.snapshot.params['id'];
    if (this.tripId) {
      this.tripService.getTrip(this.tripId).subscribe(trip => {
        this.tripForm.patchValue(trip);
      });
    }
  }

  onSubmit() {
    if (this.tripForm.valid) {
      if (this.tripId) {
        this.tripService.updateTrip(this.tripId, this.tripForm.value).subscribe(() => {
          this.router.navigate(['/planner']);
        });
      } else {
        this.tripService.createTrip(this.tripForm.value).subscribe(() => {
          this.router.navigate(['/planner']);
        });
      }
    }
  }
}