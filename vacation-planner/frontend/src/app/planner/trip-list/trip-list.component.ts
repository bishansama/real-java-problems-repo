import { Component, OnInit } from '@angular/core';
import { TripService } from '../../core/services/trip.service';
import { MatListModule } from '@angular/material/list';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { Trip } from '../../shared/models/trip.model';

@Component({
  selector: 'app-trip-list',
  standalone: true,
  imports: [CommonModule, MatListModule, MatButtonModule, RouterModule],
  templateUrl: './trip-list.component.html',
  styleUrls: ['./trip-list.component.scss']
})
export class TripListComponent implements OnInit {
  trips: Trip[] = [];

  constructor(private tripService: TripService) { }

  ngOnInit(): void {
    this.tripService.getTrips().subscribe((trips: Trip[]) => {
      this.trips = trips;
    });
  }
}
