import dayjs from 'dayjs/esm';
import { IAttendance } from 'app/entities/attendance/attendance.model';

export interface ITimeSheet {
  id: number;
  checkIn?: dayjs.Dayjs | null;
  checkOut?: dayjs.Dayjs | null;
  date?: dayjs.Dayjs | null;
  hasCheckedIn?: boolean | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  attendance?: Pick<IAttendance, 'id'> | null;
}

export type NewTimeSheet = Omit<ITimeSheet, 'id'> & { id: null };
