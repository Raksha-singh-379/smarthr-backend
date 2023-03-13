import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TimeSheetFormService, TimeSheetFormGroup } from './time-sheet-form.service';
import { ITimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';
import { IAttendance } from 'app/entities/attendance/attendance.model';
import { AttendanceService } from 'app/entities/attendance/service/attendance.service';

@Component({
  selector: 'jhi-time-sheet-update',
  templateUrl: './time-sheet-update.component.html',
})
export class TimeSheetUpdateComponent implements OnInit {
  isSaving = false;
  timeSheet: ITimeSheet | null = null;

  attendancesSharedCollection: IAttendance[] = [];

  editForm: TimeSheetFormGroup = this.timeSheetFormService.createTimeSheetFormGroup();

  constructor(
    protected timeSheetService: TimeSheetService,
    protected timeSheetFormService: TimeSheetFormService,
    protected attendanceService: AttendanceService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAttendance = (o1: IAttendance | null, o2: IAttendance | null): boolean => this.attendanceService.compareAttendance(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSheet }) => {
      this.timeSheet = timeSheet;
      if (timeSheet) {
        this.updateForm(timeSheet);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeSheet = this.timeSheetFormService.getTimeSheet(this.editForm);
    if (timeSheet.id !== null) {
      this.subscribeToSaveResponse(this.timeSheetService.update(timeSheet));
    } else {
      this.subscribeToSaveResponse(this.timeSheetService.create(timeSheet));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSheet>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(timeSheet: ITimeSheet): void {
    this.timeSheet = timeSheet;
    this.timeSheetFormService.resetForm(this.editForm, timeSheet);

    this.attendancesSharedCollection = this.attendanceService.addAttendanceToCollectionIfMissing<IAttendance>(
      this.attendancesSharedCollection,
      timeSheet.attendance
    );
  }

  protected loadRelationshipsOptions(): void {
    this.attendanceService
      .query()
      .pipe(map((res: HttpResponse<IAttendance[]>) => res.body ?? []))
      .pipe(
        map((attendances: IAttendance[]) =>
          this.attendanceService.addAttendanceToCollectionIfMissing<IAttendance>(attendances, this.timeSheet?.attendance)
        )
      )
      .subscribe((attendances: IAttendance[]) => (this.attendancesSharedCollection = attendances));
  }
}
