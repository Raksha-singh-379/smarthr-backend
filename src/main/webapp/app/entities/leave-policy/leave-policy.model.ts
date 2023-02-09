import dayjs from 'dayjs/esm';

export interface ILeavePolicy {
  id: number;
  leaveType?: string | null;
  isCarryForword?: boolean | null;
  employeeType?: string | null;
  genderLeave?: string | null;
  leaveStatus?: string | null;
  totalLeave?: string | null;
  maxLeave?: string | null;
  hasproRataLeave?: boolean | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewLeavePolicy = Omit<ILeavePolicy, 'id'> & { id: null };
