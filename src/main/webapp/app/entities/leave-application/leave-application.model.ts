import dayjs from 'dayjs/esm';

export interface ILeaveApplication {
  id: number;
  leaveType?: string | null;
  balanceLeave?: number | null;
  noOfDays?: number | null;
  reason?: string | null;
  year?: number | null;
  formDate?: dayjs.Dayjs | null;
  toDate?: dayjs.Dayjs | null;
  leaveStatus?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeId?: number | null;
  companyId?: number | null;
}

export type NewLeaveApplication = Omit<ILeaveApplication, 'id'> & { id: null };
