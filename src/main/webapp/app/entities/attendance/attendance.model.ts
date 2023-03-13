import dayjs from 'dayjs/esm';

export interface IAttendance {
  id: number;
  deviceInfo?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  date?: dayjs.Dayjs | null;
  day?: string | null;
  hours?: string | null;
  employeeId?: string | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewAttendance = Omit<IAttendance, 'id'> & { id: null };
